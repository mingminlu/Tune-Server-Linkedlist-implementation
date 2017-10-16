import java.net.*;
import java.io.*;
import java.util.*;
/**
 * A test file to test all correct input
 * and some error.
 * @author Mingmin
 *
 */
public class LikeTuneServerTest {
	public static void main(String[] args) {
		//a message to indicate that user has finished input.
		String endMss="done";
		//to control the whole test.
		Scanner control=new Scanner(System.in);
		try {
			  System.out.println("This is a test, so there will be some usual input.");
			  //start new connection to server.
			  Socket client = new Socket("localhost", 12058);
			  //control the test step by step.
			  System.out.println("input anything to test addTune");
			  control.next();
			  BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			  PrintWriter  out = new PrintWriter (new OutputStreamWriter(client.getOutputStream()));
			  out.println("0\nDeerhoof\nWe do Parties\n"+endMss);// add tune
			  out.println("0\nFKA Twigs\nHours\n"+endMss);
			  out.println("0\nDeerhoof\nFlower\n"+endMss);
			  out.println("0\nThe Necks\nRum Jungle\n"+endMss);
			  out.println("0\nManu Chao\nLes Milles Paillettes\n"+endMss);
			  out.flush();
			  Socket client2=new Socket("localhost",12058);
			  System.out.println("input anything to test another.");//set up another client to connect the server.
			  control.next();
			  BufferedReader in2 = new BufferedReader (new InputStreamReader(client2.getInputStream()));
			  PrintWriter out2 = new PrintWriter (new OutputStreamWriter(client2.getOutputStream()),true);
			  out2.println("2\n"+endMss);//send a message to server by another client
			  out2.flush();
			  String line2=in2.readLine();//display the result.
			  while (!(line2.equals("finish"))){
				     System.out.println(line2);
				     line2=in2.readLine();
				  }  
			  client2.close();
			  client.close();
			  //start new connection to server.
			  client=new Socket("localhost",12058);
			  //control the test step by step.
			  System.out.println("input anything to test LikeTune");
			  control.next();
			  in = new BufferedReader (new InputStreamReader(client.getInputStream()));
			  out = new PrintWriter (new OutputStreamWriter(client.getOutputStream()),true);
			  out.println("1\nDeerhoof\nWe do Parties\n"+endMss); // like tune
			  out.println("1\nDeerhoof\nWe do Parties\n"+endMss);  
			  out.println("1\nDeerhoof\nFlower\n"+endMss);
			  out.println("1\nThe Necks\nRum Jungle\n"+endMss);
			  out.println("1\nThe Necks\nRum Jungle\n"+endMss);
			  out.println("1\nThe Necks\nRum Jungle\n"+endMss);
			  out.flush();
			  client.close();
			  //start new connection to server.
			  client=new Socket("localhost",12058);
			  //control the test step by step.
			  System.out.println("input anything to test list by Alphabetical order.");
			  control.next();
			  in = new BufferedReader (new InputStreamReader(client.getInputStream()));
			  out = new PrintWriter (new OutputStreamWriter(client.getOutputStream()));
			  out.print("2\n"+endMss);//list by Alphabetical order.
			  out.flush();
			  String line=in.readLine();
			  while (!(line.equals("finish"))){
				     System.out.println(line);
				     line=in.readLine();
				  }  
			  client.close();  
			  //start new connection to server.
			  client=new Socket("localhost",12058);
			  //control the test step by step.
			  System.out.println("input anything to test list by like order.");
			  control.next();
			  in = new BufferedReader (new InputStreamReader(client.getInputStream()));
			  out = new PrintWriter (new OutputStreamWriter(client.getOutputStream()));
			  out.print("3\n"+endMss);//list by like order.
			  out.flush();
			  line=in.readLine();
			  //loop to display the result.
			  while(!(line.equals("finish"))){
				     System.out.println(line);
				     line=in.readLine();
			  }  
			  client.close();
			  //start new connection to server.
			  client=new Socket("localhost",12058);
			  //control the test step by step.
			  System.out.println("input anything to test error 4");
			  control.next();
			  in = new BufferedReader (new InputStreamReader(client.getInputStream()));
			  out = new PrintWriter (new OutputStreamWriter(client.getOutputStream()));
			  out.println("0\n"+endMss);//test error 4 by end unexpectly.
			  out.flush();
			  line=in.readLine();
			  //loop to display the result.
			  while(!(line.equals("finish"))){
				     System.out.println(line);
				     line=in.readLine();
			  }  
			  client.close();
			  //start new connection to server.
			  client=new Socket("localhost",12058);
			  //control the test step by step.
			  System.out.println("input anything to test error 5");
			  control.next();
			  in = new BufferedReader (new InputStreamReader(client.getInputStream()));
			  out = new PrintWriter (new OutputStreamWriter(client.getOutputStream()));
			  out.println("0\n\n\n"+endMss);//test error 5 by input empty line.
			  out.flush();
			  line=in.readLine();
			  //loop to display the result.
			  while(!(line.equals("finish"))){
				     System.out.println(line);
				     line=in.readLine();
			  }  
			  client.close();
			  //start new connection to server.
			  client=new Socket("localhost",12058);
			  //control the test step by step.
			  System.out.println("input anything to test error 7");
			  control.next();
			  in = new BufferedReader (new InputStreamReader(client.getInputStream()));
			  out = new PrintWriter (new OutputStreamWriter(client.getOutputStream()));
			  out.println("7\n"+endMss);//test error 7 by input wrong command.
			  out.flush();
			  line=in.readLine();
			  //loop to display the result.
			  while(!(line.equals("finish"))){
				     System.out.println(line);
				     line=in.readLine();
			  }  
			  client.close();
			  client=new Socket("localhost",12058);
			  //control the test step by step.
			  System.out.println("input anything to test list by error 6.");
			  control.next();
			  in = new BufferedReader (new InputStreamReader(client.getInputStream()));
			  out = new PrintWriter (new OutputStreamWriter(client.getOutputStream()));
			  out.print("2\n"+endMss);//list by Alphabetical order.
			  out.flush();
			  in.close();//close the stream to throw IOException.
			  line=in.readLine();
			  client.close();  
			} catch (UnknownHostException e) {
				 //cannot connect to server.
			System.err.println(e.getMessage());
			} catch (IOException e) {
				//I/O error.
			System.out.println("error 6: "+e.getMessage());
		}
	}
}
