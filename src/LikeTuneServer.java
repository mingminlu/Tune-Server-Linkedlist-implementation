import java.io.*;
import java.net.*;
import java.util.*;
/**
 * Set up a LikeTune Server to accept different clients
 * to connect it.
 * @author Mingmin
 *
 */
public class LikeTuneServer {
	//port number for the server
	private static final int portNum=12058;
	//the server socket
	private static ServerSocket ss=null;
	//set when shutDown() is called to close the server.
	private static boolean shutDown=false;
	//a tunelist which saved by the server.
	private static TuneList list=new TuneList();
/**
 *shut down the server by closing the server socket.  
 */
public static void shutDown(){
	//a symbol that server has been closed.
	shutDown=true;
	try{
		/*
         *  close the server socket;
         *  call of accept() in main will throw a SocketException
         */
		ss.close();
		} catch (Exception e)	{
		System.err.println("probelm shutting down: ");
		System.err.println(e.getMessage());
		System.exit(1);
		}
	}
public static void main(String args[]){
	//for client to connect. 
	Socket client;
	//session-handling thread.
	Thread t;
	ServerSocket ss=null;
	try{
		//set up the server socket.
		ss=new ServerSocket(portNum);
		System.out.println("server is running");
		while(true){
			if(ss==null)
				return;
			client=ss.accept();
			//start the thread.
			t=new Thread(new LikeTuneHandler(client,list));
			t.start();
			} 
		}catch(SocketException se){
			 /*
	          * will be thrown when accept() is called after closing
	          * the server socket, in method shutDown().
	          * If shutDownCalled, then simply exit; otherwise,
	          * something else has happened:
	          */
				if (! shutDown){
					System.err.println("socket problem:");
					System.err.println(se.getMessage());
					// trust the JVM to clean up
					System.exit(1);
				}
			}catch(IOException e){
			System.err.println("I/O error:");
			System.err.println(e.getMessage());
			System.exit(1);
			}
	finally{
		if(ss!=null){
			try{
				ss.close();
			}catch (Exception e){
				System.err.println("closing"+e.getMessage());
				}
			}
		}
	}
}
/*
 *  Session-handler class to handle one remote client
 *  in a separate thread.
 */
class LikeTuneHandler implements Runnable{
	private Socket client=null;
	private String art=null;
	private String title=null;
	private TuneList list=new TuneList();
	private final String endMss="finish\n";
	static String lock=new String("1");
public LikeTuneHandler(Socket s, TuneList l){
		client=s;
		list=l;
	}
	/*
	 *  Handle session for one remote client:
	 *  set up I/O, then repeat to accept
	 *  command from client's input till
	 *  client sends "end" or close server by
	 *  "shutdown".
	 */
	public void run(){
		//input/output stream.
		BufferedReader in=null;
		PrintWriter out=null;
		try{
			boolean done1=false;
			//set up I/O stream.
			in=new BufferedReader(new InputStreamReader(client.getInputStream()));
			out=new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
			//welcome message.
			out.println("Enter lines start with:");
			out.println("0 means add tune");
			out.println("1 means like a tune");
			out.println("2 means viewing tunes in alphabetical order");
			out.println("3 means viewing in popular order");
			out.println("enter end to quit");
			out.flush();
			//first loop to control whole thread
			while(!done1){
				boolean done2=false;
				String line;
			//second loop to control user input.
			while(!done2){
				line=in.readLine();
				if ((line==null)||(line.trim().equals("end"))){
					//quit, but keep server running.
					done2=true;
					}else if (line.trim().equals("shutdown")){
						//shut down the server.
						LikeTuneServer.shutDown();
						return;
						//accept the command 0.
					}
					else if(line.equals("0")){
						line=in.readLine();
						int count=0;
						//count the number of line to judge error 4.
						while(!(line.equals("done"))){
							if(count==0){
								art=line;
								count++;
								line=in.readLine();
							}else if(count==1){
								title=line;
								count++;
								line=in.readLine();
							}
						}
						if(count!=2){
							out.print("error 4: client input ends unexpectly.\n"+endMss);	
							out.flush();
							done1=true;
							//judge error 5
							}else if(art.equals("")||title.equals("")){
								out.print("error 5: empty line\n"+endMss);
								out.flush();	
								done1=true;
							}else{
							//if user input correct it will add tune to list.
								list.addTune(art,title);
							}
							//accept the command 1.
							}else if(line.equals("1")){
								//synchronizer for liketune.
								synchronized(lock){
									line=in.readLine();
									int count=0;
									//count the number of line to judge error 4.
									while(!(line.equals("done"))){
										if(count==0){
											art=line;
											count++;
											line=in.readLine();
										}else if(count==1){
											title=line;
											count++;
											line=in.readLine();
										}
									}
									if(count!=2){
										out.print("error 4: client input ends unexpectly.\n"+endMss);	
										out.flush();
										done1=true;
										//judge error 5
									}else if(art==null||title==null){
										out.print("error 5: empty line\n"+endMss);
										out.flush();	
										done1=true;
									}else{
								//if user input correct it will like the tune in list.
								list.likeTune(art,title);
								}
							}
							//accept the command 2 to list by Alphabetically.
					}else if(line.equals("2")){
						out.println(list.listAlphabetically()+endMss);
						out.flush();
						done2=true;
						//accept the command 3 to list by likes.
					}else if(line.equals("3")){
						out.println(list.listByLikes()+endMss);
						out.flush();
						done2=true;
						//if the user input cannot be recognised, error 7.
					}else {
						out.print("error 7: unrecognised request.\n"+endMss);
						out.flush();
						done2=true;
						done1=true;
					}
				}
			}
		}catch(IOException e){
			/* 
			 * fatal error for this session and causing the disconnect server.
			 * Any I/O exception cause error 6.
			 */
			System.err.println("error 6: "+e.getMessage()+endMss);
		}
		finally{
			try{
				in.close();
			}catch(IOException e){
				System.err.println("error 6: "+e.getMessage()+endMss);
			}if(out!=null){
			out.close();
			}if(client!=null){
				try{
					client.close();
				}catch(IOException e){
					System.err.println("error 6: "+e.getMessage()+endMss);
				}
			}
		}
	}
}