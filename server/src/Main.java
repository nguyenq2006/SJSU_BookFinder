import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		System.out.println("Hello World!!!!!");
		DataManager.sharedInstance().loadUserData();
		DataManager.sharedInstance().loadBookData();
		HTTPServer.startServer();
	}

}
