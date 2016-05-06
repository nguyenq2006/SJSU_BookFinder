import java.io.FileNotFoundException;
import java.util.*;
public class AddBookController {

	private Book model; //the model of the Book Object

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
		dbm.addBook(model, false);
	}


/**
	 * Converting Book object to String object
	 * @param model - book info from Book class
	 * @return resultString - from Book object to String object
	 */

	public static String modelToString(Book m) {
		String resultString = 
				"ISBN: " + m.getIsbn() + " "
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
		
		while(in.hasNextLine())
		{
			String bookLine = in.nextLine().trim();
//			System.out.println(bookLine);
			int colon = bookLine.indexOf(':');
			int star = bookLine.indexOf('*');
			int poundSign = bookLine.indexOf('#', bookLine.indexOf('#')+1);
			if(bookLine.contains("ISBN: ")) {
				//Get ISBN
				ISBN = bookLine.substring(colon+1, star).trim();
				
//				bookLine = bookLine.replace(ISBN, "");
//				bookLine = bookLine.replace("ISBN:", "");
//				bookLine = bookLine.replace("*&#$&!@# Book ", "").trim();
				bookLine = bookLine.substring(++poundSign).trim();
			}
			
			colon = bookLine.indexOf(':');
			star = bookLine.indexOf('*');
			poundSign = bookLine.indexOf('#', bookLine.indexOf('#')+1);
			if(bookLine.contains("Title: ")) {
				//Get book title
				bookTitle = bookLine.substring(colon+1, star).trim();
				
//				bookLine = bookLine.replace("Title: ", "");
//				bookLine = bookLine.replace("*&#$&!@# A", "");
//				bookLine = bookLine.replace(bookTitle, "");
				bookLine = bookLine.substring(++poundSign).trim();
				
			}
			
			colon = bookLine.indexOf(':');
			star = bookLine.indexOf('*');
			poundSign = bookLine.indexOf('#', bookLine.indexOf('#')+1);
			if(bookLine.contains("uthor: ")) {
				//Get Author
				author = bookLine.substring(colon+1, star).trim();
				
//				bookLine = bookLine.replace("uthor: ", "");
//				bookLine = bookLine.replace(author, "");
//				bookLine = bookLine.replace("*&#$&!@# P", "");
				bookLine = bookLine.substring(++poundSign).trim();
				
			}
			
			colon = bookLine.indexOf(':');
			star = bookLine.indexOf('*');
			poundSign = bookLine.indexOf('#', bookLine.indexOf('#')+1);
			if(bookLine.contains("rice: ")) {
				//Get book price
				String price = bookLine.substring(colon+1, star).trim();
				bookPrice = Double.parseDouble(price);
				
//				bookLine = bookLine.replace("rice:", "");
//				bookLine = bookLine.replace(price, "");
//				bookLine = bookLine.replace("*&#$&!@# Student ", "");
				bookLine = bookLine.substring(++poundSign).trim();
			}
			
			colon = bookLine.indexOf(':');
			star = bookLine.indexOf('*');
			poundSign = bookLine.indexOf('#', bookLine.indexOf('#')+1);
			if(bookLine.contains("ID: ")) {
				//Get Student ID
				String id = bookLine.substring(colon+1, star).trim();
				studentID = Long.parseLong(id);
//				bookLine = bookLine.replace("ID:", "");
//				bookLine = bookLine.replace(id, "");
//				bookLine = bookLine.replace("*&#$&!@# HardCover?", "");
				bookLine = bookLine.substring(++poundSign).trim();
			}
			
			colon = bookLine.indexOf(':');
			if(bookLine.contains(": ")) {
				//Get boolean value
				String booleanValue = bookLine.substring(colon+1, bookLine.length());
				isHardCover = Boolean.parseBoolean(booleanValue);
			}	
		} in.close();
		


		Book book = new Book(ISBN, bookTitle, author, bookPrice, studentID, isHardCover);
		return book;
	}

}
