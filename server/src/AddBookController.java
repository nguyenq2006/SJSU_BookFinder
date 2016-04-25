import java.io.FileNotFoundException;
import java.util.*;
public class AddBookController {

	Book model; //the model of the Book Object

	/**
	 * Constructing an add book controller to add book info to database
	 * @throws FileNotFoundException 
	 * @params - Information of isbn, title, author, price, studentId, hardcover from Book object
	 */
	public AddBookController(Map<String, Object> params) throws FileNotFoundException 
	{
		String isbn = (String) params.get("isbn");
		String title = (String) params.get("title");
		String author = (String) params.get("author");
		double price = Double.parseDouble((String)params.get("price"));
		long studentID = Long.parseLong((String)params.get("id"));
		boolean isHardCover =  Boolean.parseBoolean((String)params.get("hardcover"));

		model = new Book(isbn, title, author, price, studentID, isHardCover);

		//test comment

		//Save book information to database
		this.saveToDataBase(this.model);
	}

	/**
	 * Saving Book information to DatabaseManager
	 * @param modelString - the model to save to database
	 * @throws FileNotFoundException 
	 */
	private void saveToDataBase(Book model) throws FileNotFoundException{
		DataManager dbm = DataManager.sharedInstance();
		dbm.addBook(model);
	}


/**
	 * Converting Book object to String object
	 * @param model - book info from Book class
	 * @return resultString - from Book object to String object
	 */
	
	public static String modelToString(Book m)
	{
				String resultString = 
				"ISBN: " + m.getIsbn() 
						+ "*&#$&!@#"   + " " +
						"Book Title: " + m.getBookTitle() + " " 
						+ "*&#$&!@#" + " " +
						"Author: " + m.getAuthor() + " "
						+ "*&#$&!@#" + " " +
						"Price: " + m.getPrice() + " "
						+ "*&#$&!@#" + " " +
						"Student ID: " + m.getID() + " " 
						+ "*&#$&!@#" + " " + 
						"HardCover?: " + m.isHardCover() + " " + "\n";

		return resultString;
	}

	/**
	 * Converting String object to Book object
	 * @param bookInfo - the book information in String 
	 * @return bookObject - final conversion of String object -> Book object
	**/
	
	public static Book stringToModel(String bookInfo)
	{
		Scanner in = new Scanner(bookInfo);
		

		String ISBN = "";
		String bookTitle = "";
		String author = "";
		double bookPrice = 0;
		long studentID = 0;
		boolean isHardCover = false;
		String hardCoverResult = "";

		while(in.hasNextLine())
		{
			String line = in.nextLine().trim();
			int colon = line.lastIndexOf(':');
			
			if(line.contains("ISBN: ")) 
			{
				ISBN = line.substring(colon+1, line.length()).trim();
			}
			
			else if (line.contains("Book Title: "))
			{
				bookTitle = line.substring(colon+1, line.length()).trim();
			}
			
			else if (line.contains("Author: ")) 
			{
				author = line.substring(colon+1, line.length()).trim();
			}
			
			else if (line.contains("Price: "))
			{
				bookPrice = Double.parseDouble(line.substring(colon+1, line.length()).trim());
			}
			
			else if (line.contains("Student ID: "))
			{
				studentID = Long.parseLong(line.substring(colon+1, line.length()).trim());
			}
			
			else if (line.contains("HardCover?: "))
			{
				hardCoverResult = line.substring(colon+1, line.length()).trim();
				isHardCover = Boolean.parseBoolean(hardCoverResult);
			}
			
		} in.close(); 
		
		
		Book book = new Book(ISBN, bookTitle, author, bookPrice, studentID, isHardCover);
		return book;
	} 
}
