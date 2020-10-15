import java.io.InputStream;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class Client {
	
	
	/**
	 * Access server time in standard structure.
	 */
	public interface ServerTime {
	    int getSecond ();           // Gets the second-of-minute field.
	    int getMinute ();           // Gets the minute-of-hour field.
	    int getHour ();             // Gets the hour-of-day field.
	    int getDayOfMonth ();       // Gets the day-of-month field.
	    Month getMonth ();          // Gets the month-of-year field.
	    int getYear ();             // Gets the year field.
	    DayOfWeek getDayOfWeek ();  // Gets the day-of-week field.
	    int getDayOfYear ();        // Gets the day-of-year field.
	}

	static CurrentServerTime getCurrentServerTime(String time)
	{
		try
		{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Shared.stringFormat);
			LocalDateTime lt = LocalDateTime.parse(time, formatter);
			return new CurrentServerTime(lt);
			
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	static class CurrentServerTime implements ServerTime
	{
		LocalDateTime time;
		
		public CurrentServerTime(LocalDateTime time) {
			this.time = time;
		}

		@Override
		public int getSecond() {
			return time.getSecond();
		}

		@Override
		public int getMinute() {
			return time.getMinute();
		}

		@Override
		public int getHour() {
			return time.getHour();
		}

		@Override
		public int getDayOfMonth() {
			return time.getDayOfMonth();
		}

		@Override
		public Month getMonth() {
			return time.getMonth();
		}

		@Override
		public int getYear() {
			return time.getYear();
		}

		@Override
		public DayOfWeek getDayOfWeek() {
			return time.getDayOfWeek();
		}

		@Override
		public int getDayOfYear() {
			return time.getDayOfYear();
		}
		
	}
	
    public static void main (String [] args) {

        // Create a socket object and connect it to the server.
        try (Socket server_socket = new Socket (Shared.SERVER_ADDR, Shared.SERVER_PORT)) {

            System.out.println ("Established outgoing connection.");

            try (
                // Wrap the socket streams in appropriate readers and writers.
                InputStream input = server_socket.getInputStream ();
                OutputStream output = server_socket.getOutputStream ();
                InputStreamReader reader = new InputStreamReader (input);
                BufferedReader buffered = new BufferedReader (reader);
                PrintWriter writer = new PrintWriter (output, true);
            ) {
                // Just send and receive something.
                writer.println("H");
               String recieved = buffered.readLine();
               CurrentServerTime cst = getCurrentServerTime(recieved);
               System.out.printf("Day: %d\nMonth: %d\nYear: %d", cst.getDayOfMonth(), cst.getMonth().getValue(), cst.getYear());
            }
        }
        catch (Exception e) {
            System.out.println (e);
        }
    }
}
