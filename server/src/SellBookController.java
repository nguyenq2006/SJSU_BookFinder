import java.util.*;

public class SellBookController extends HttpRequestController {
	private Book model;
	private Person user;
	String response = "";


	/**
	 * After Book is sold, remove from BookTree and remove ISBN from user
	 * @param params
	 */
	public SellBookController(Map<String, Object> params) 
	{
		DataManager dm = DataManager.sharedInstance();

		/* Remove book based on ISBN */
		for(Map.Entry<String, Object> entry : params.entrySet()) {
			String isbn = "" + entry.getValue();
			dm.remove(isbn);
		}
			
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
			System.out.println(bookLine);
			int colon = bookLine.indexOf(':');
			int star = bookLine.indexOf('*');
			if(bookLine.contains("ISBN: ")) {
				//Get ISBN
				ISBN = bookLine.substring(colon+1, star).trim();

				bookLine = bookLine.replace(ISBN, "");
				bookLine = bookLine.replace("ISBN:", "");
				bookLine = bookLine.replace("*&#$&!@# Book ", "").trim();
			}

			int colon2 = bookLine.indexOf(':');
			int star2 = bookLine.indexOf('*');
			if(bookLine.contains("Title: ")) {
				//Get book title
				bookTitle = bookLine.substring(colon2+1, star2).trim();

				bookLine = bookLine.replace("Title: ", "");
				bookLine = bookLine.replace("*&#$&!@# A", "");
				bookLine = bookLine.replace(bookTitle, "");


			}

			int colon3 = bookLine.indexOf(':');
			int star3 = bookLine.indexOf('*');
			if(bookLine.contains("uthor: ")) {
				//Get Author
				author = bookLine.substring(colon3+1, star3).trim();

				bookLine = bookLine.replace("uthor: ", "");
				bookLine = bookLine.replace(author, "");
				bookLine = bookLine.replace("*&#$&!@# P", "");


			}

			int colon4 = bookLine.indexOf(':');
			int star4 = bookLine.indexOf('*');
			if(bookLine.contains("rice: ")) {
				//Get book price
				String price = bookLine.substring(colon4+1, star4).trim();
				bookPrice = Double.parseDouble(price);

				bookLine = bookLine.replace("rice:", "");
				bookLine = bookLine.replace(price, "");
				bookLine = bookLine.replace("*&#$&!@# Student ", "");
			}

			int colon5 = bookLine.indexOf(':');
			int star5 = bookLine.indexOf('*');
			if(bookLine.contains("ID: ")) {
				//Get Student ID
				String id = bookLine.substring(colon5+1, star5).trim();
				studentID = Long.parseLong(id);
				bookLine = bookLine.replace("ID:", "");
				bookLine = bookLine.replace(id, "");
				bookLine = bookLine.replace("*&#$&!@# HardCover?", "");

			}

			int colon6 = bookLine.indexOf(':');
			if(bookLine.contains(": ")) {
				//Get boolean value
				String booleanValue = bookLine.substring(colon6+1, bookLine.length());
				isHardCover = Boolean.parseBoolean(booleanValue);
			}	
		} in.close();



		Book book = new Book(ISBN, bookTitle, author, bookPrice, studentID, isHardCover);
		return book;
	}

	public String getResponse()
	{
		return this.response;
	}



}
