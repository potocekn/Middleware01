import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Server {
    public static void main (String [] args) {

        // Create a server socket object and start listening for incoming connections.
        try (ServerSocket server_socket = new ServerSocket (Shared.SERVER_PORT)) {

            System.out.println ("Waiting for incoming connection.");

            while (true) {
                // Wait until an incoming connection arrives and accept it.
                try (Socket client_socket = server_socket.accept ()) {

                    System.out.println ("Accepted an incoming connection.");

                    try (
                        InputStream input = client_socket.getInputStream ();
                        OutputStream output = client_socket.getOutputStream ();
                    	BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    ) {           
                            String data = reader.readLine();
	                        if (data == null) break;
	                        
	                        /**/LocalDateTime time = LocalDateTime.now(); 
	                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Shared.stringFormat);
	                        String timeString = time.format(formatter);
	                        output.write (timeString.getBytes());/**/                           
                    }
                    System.out.println ("Client disconnected.");
                }
                catch (Exception e) {
                    System.out.println (e);
                }
            }
        }
        catch (Exception e) {
            System.out.println (e);
        }
    }
}
