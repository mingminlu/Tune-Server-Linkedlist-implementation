import java.net.*;
import java.io.*;
import java.util.*;
/**
 * A test file to test all correct input
 * and some error.
 * @author Mingmin
 *
 */
public class Client {
	public static void main(String[] args) {
		//a message to indicate that user has finished input.
		String endMss="done";
		//to control the whole test.
		Scanner control=new Scanner(System.in);
		try {
			  System.out.println("This is a test, so there will be some usual input.");
			  //start new connection to server.
			  //control the test step by step.
			 for(int i=0;i<10000;i++){
				 Socket client=new Socket("localhost",12058);
				 BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				  PrintWriter  out = new PrintWriter (new OutputStreamWriter(client.getOutputStream()));
				  out.println("1\nDeerhoof\nWe do Parties\n"+endMss);
				  out.print("3\n"+endMss);
				  out.flush();
				  String line=in.readLine();
				  while (!(line.equals("finish"))){
					     System.out.println(line);
					     line=in.readLine();
					  }  
			 }
			} catch (UnknownHostException e) {
				 //cannot connect to server.
			System.err.println(e.getMessage());
			} catch (IOException e) {
				//I/O error.
			System.out.println("error 6: "+e.getMessage());
		}
	}
}
