import java.io.IOException;

/**
 * Main class to start the server 
 */
public class Main {
	
	/**
	 * Starts the server and loads the data from the database -> (which is a File)
	 * @param args String arguments passed in from console
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("Hello World!!!!!");
		DataManager.sharedInstance().load();
		HTTPServer.startServer();
	}

}
