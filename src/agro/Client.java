package agro;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
 
public class Client implements Runnable {
     private static int port = 10010; /* port to listen on */
        private Socket client;

        Client(Socket client) {
            this.client = client;
        }

        public void run() {
            BufferedReader in = null;
            PrintWriter out = null;
             try {
                /* obtain an input stream to this client ... */
                in = new BufferedReader(new InputStreamReader(
                            client.getInputStream()));
                /* ... and an output stream to the same client */
               out = new PrintWriter(
                       
                        client.getOutputStream(), true);
           } catch (IOException e) {
                System.err.println(e);
                return;
            }

            String msg;
           
            try {
                /* loop reading messages from the client, 
                 * output to stdin and send back an "OK" back */
                if ((msg = in.readLine()) != null){
                 System.out.println("Client says: " + msg);
                 out.println("ok");
                }} catch (IOException e) {
                System.err.println("eren");
            }
        }
    
    
 @SuppressWarnings("resource")
public static void main (String[] args) throws IOException{
       ServerSocket server = null;
       server = new ServerSocket(port); /* start listening on the port */
       Socket client = null;
        while(true) {
            try {
                client = server.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.err.println(e);
                System.exit(1);
            }
            /* start a new thread to handle this client */
            Thread t = new Thread(new Client(client));
            t.start();
        }
    }
}
