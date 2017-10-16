/*
 * Based on an example from Horstman & Cornell,
 * Core Java, 2 vols., Prentice Hall 2002, 2001.
 *
 */

import java.io.*;
import java.net.*;

public class ThreadedEchoServer
{
   // port number for the server
   private static final int PORT_NUM = 8189;

   // the server socket:
   private static ServerSocket ss;

   // set when shutDown() is called to stop the server:
   private static boolean shutDownCalled = false;

   // shut the server down by closing the server socket
   public static void shutDown()
   {
      // flag that the server socket has been closed
      shutDownCalled = true;

      try 
      {
         /*
          *  close the server socket;
          *  call of accept() in main will throw a SocketException
          */
         ss.close();
      }
      catch (Exception e)
      {
         // something went wrong; give data:
         System.err.println("problem shutting down:");
         System.err.println(e.getMessage());
         // and trust the JVM to clean up:
         System.exit(1);
      }
   }

   public static void main(String[] args ) 
   {
      // for client connections
      Socket incoming;

      // session-handling thread
      Thread t;

      try 
      {
         // set up server socket
         ss = new ServerSocket(PORT_NUM);

         while (true)
         {
            incoming = ss.accept();

            // start session-handler in new thread
            t = new Thread(new EchoHandler(incoming));
            t.start();
         }
      }
      catch (SocketException se)
      {
         /*
          * will be thrown when accept() is called after closing
          * the server socket, in method shutDown().
          * If shutDownCalled, then simply exit; otherwise,
          * something else has happened:
          */
         if (! shutDownCalled)
         {
            System.err.println("socket problem:");
            System.err.println(se.getMessage());
            // trust the JVM to clean up
            System.exit(1);
         }
      }
      catch (IOException ioe)
      {
         System.err.println("I/O error:");
         System.err.println(ioe.getMessage());
         System.exit(1);
      }
      finally
      {
         if (ss != null)
         {
            try 
            {
               ss.close();
            }
            catch (Exception e)
            {
               System.err.println("closing: " + e.getMessage());
            }
         }
      }
   }
}

/*
 *  Session-handler class to handle one remote client
 *  in a separate thread.
 */
class EchoHandler implements Runnable
{
   // the connection to the remote client
   private Socket client;

   EchoHandler(Socket s)
   {
      client = s;
   }

   /*
    *  Handle session for one remote client:
    *  set up I/O, then repeat all input till
    *  client sends "BYE"
    */
   public void run()
   {
      // for I/O
      BufferedReader in = null;
      PrintWriter out = null;

      try 
      {
         // set up I/O
         in = new BufferedReader
           (new InputStreamReader(client.getInputStream()));
         out = new PrintWriter
           (new OutputStreamWriter(client.getOutputStream()));

         // prompt client
         out.println("Hello! Enter BYE to exit.");
         out.flush();

         // for client input
         String line;
         boolean done = false;
         while (!done)
         {
            line = in.readLine();
 
            if ((line == null)
                || (line.trim().equals("BYE")))
            {
               // quit
               done = true;
            }
            else if (line.trim().equals("skasmos"))
            {
               // shut down server
               ThreadedEchoServer.shutDown();
               return;
            }
            else
            {
               out.println("Echo: " + line);
               out.flush();
            }
         }
      }
      catch (IOException e)
      {
         // fatal error for this session
         System.err.println(e.getMessage());
      }
      finally
      {  // close connections
         try 
         {
            in.close();
         }
         catch(IOException e)
         {
         }
         if (out != null)
         {
            out.close();
         }
         if (client != null)
         {
            try 
            {
               client.close();
            }
            catch (IOException e)
            {
            }
         }
      }
   }

}
