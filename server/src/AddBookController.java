import java.util.*;
public class AddBookController {

	Book model; //the model of the Book Object

	/**
	 * Constructing an add book controller to add book info to database
	 * @params - Information of isbn, title, author, price, studentId, hardcover from Book object
	 */
	public AddBookController(Map<String, Object> params) 
	{
		String isbn = (String) params.get("isbn");
		String title = (String) params.get("title");
		String author = (String) params.get("author");
		double price = (double) params.get("price");
		long studentID = (long) params.get("id");
		boolean isHardCover = (boolean) params.get("hardcover");

		model = new Book(isbn, title, author, price, studentID, isHardCover);

		//test comment

		//Save book information to database
		//this.saveToDataBase(modelToString(this.model));
	}

	/**
	 * Saving Book information to DatabaseManager
	 * @param modelString - the model to save to database
	 */
	private void saveToDataBase(String modelString){
		//  DatabaseManager dbm = DatabaseManager.sharedInstance();
		//	dbm.saveNewUser(modelString);
	}


	/**
	 * Converting Book object to String object
	 * @param model - book info from Book class
	 * @return resultString - from Book object to String object
	 */
	//Change to private afterwards
	public String modelToString(Book m)
	{
				String resultString = 
				"ISBN: " + m.getIsbn() + "\n"
						+ "*&#$&!@#"   + " " + "\n" +
						"Book Title: " + m.getBookTitle() + " " + "\n"
						+ "*&#$&!@#" + " " + "\n" +
						"Author: " + m.getAuthor() + " " + "\n" 
						+ "*&#$&!@#" + " " + "\n" +
						"Price: " + m.getPrice() + " " + "\n"
						+ "*&#$&!@#" + " " + "\n" +
						"Student ID: " + m.getID() + " " + "\n"
						+ "*&#$&!@#" + " " + "\n" +
						"HardCover?: " + m.isHardCover() + " " + "\n";

		return resultString;
	}

	/**
	 * Converting String object to Book object
	 * @param bookInfo - the book information in String 
	 * @return bookObject - final conversion of String object -> Book object
	**/
	//Change to private afterwards
	public Book stringToModel(String bookInfo)
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