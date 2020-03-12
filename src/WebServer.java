import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class WebServer {

	public static void main(String[] args) {
		String fileName = "";
		try {
			// Create the server socket for the client to connect to.
			ServerSocket server = new ServerSocket(4000);

			// Establish the connection.
			Socket connect = server.accept();
			
			// Establish the input stream for the socket.
			BufferedReader input = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			
			// Parse the request that was received.
			String line = input.readLine();
			line.trim();
			String[] strings = line.split("/");
			String fileNameAndProtocol = strings[1];
			String[] strings1 = fileNameAndProtocol.split(" ");
			fileName = strings1[0];
			
			// Establish the output stream for the socket.
			PrintWriter output = new PrintWriter(new OutputStreamWriter(connect.getOutputStream()));
			BufferedOutputStream dataOutput = new BufferedOutputStream(connect.getOutputStream());
			
			// Initial work for the file requested.
			File file = null;
			FileInputStream fileIn = null;
			file = new File(fileName);
			byte[] fileData = new byte[(int) file.length()];
			try {
				// Try reading the file.
				fileIn = new FileInputStream(file);
				fileIn.read(fileData);
				output.println("HTTP/1.1 200 OK");
			} catch (FileNotFoundException fnf) {
				// File not found!
				output.println("HTTP/1.1 404 Not Found");
				output.println("");
			}
			
			// Defensive coding in case we didn't find the file.
			if (fileIn != null) {
				// Print the response!
				output.println("Date: " + getServerTime());
				output.println("Content-Length: " + file.length());
				output.println("Connection: close");
				output.println("Content-Type: text/html");
				output.println("\n");
				output.flush();
				
				// Don't forget to flush!...
				dataOutput.write(fileData, 0, (int) file.length());
				dataOutput.flush();
			}
			
			// Close the connections.
			output.close();
			input.close();
			fileIn.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String getServerTime() {
	    Calendar calendar = Calendar.getInstance();
	    SimpleDateFormat dateFormat = new SimpleDateFormat(
	        "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
	    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
	    return dateFormat.format(calendar.getTime());
	}

}
