package agro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
 
import java.net.Socket;
import java.net.UnknownHostException;
 
public class Answer {
    private static int port = 1001; /* port to connect to */
    private static String host = "192.168.101.50"; /* host to connect to */
 
    public static void main (String[] a) throws IOException {
 
        Socket server = null;
 
        try {
            /* try to open a socket to the server at the given host:port */
            server = new Socket(host, port);
        } catch (UnknownHostException e) {
            System.err.println(e);
            System.exit(1);
        }
 
        /* obtain an output stream to the server... */
        PrintWriter out = new PrintWriter(server.getOutputStream(), true);
        /* ... and an input stream */
        BufferedReader in = new BufferedReader(new InputStreamReader(
                    server.getInputStream()));
        /* stdin stream */
        BufferedReader stdIn = new BufferedReader(
                new InputStreamReader(System.in));
 
        String msg;
        System.out.println("//      Dest      //     Source     //Type//P//Status// Data //");
        /* loop reading messages from stdin, send them to the server 
         * and read the server's response */
       while ((msg = stdIn.readLine()) != null) {
    	       out.println(msg);
                System.out.println(in.readLine());
               //client.main(null);
            }
            //if ((msg = in.readLine()) != null) {
            //	System.out.println("Server says: " + msg);
            //   out.println("OK");
            //}
       //} 
    }
}
