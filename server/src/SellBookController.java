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
		TreeMap<String, Book> isbnTree = dm.getIsbnTreeMap();
		ArrayList<String> isbnList = new ArrayList<String>(); //To get isbn from Person object
		ArrayList<String> storeISBN = new ArrayList<String>(); //the updated isbn after removal

		/* Remove book based on ISBN */
		removeBook(params, isbnTree);


		/* Any remaining ISBN goes to book model
		 * Method should only remove one book at a time */
		for(String isbn : isbnTree.keySet()) {
			model = dm.getBook(isbn); //get book by isbn
			user = dm.getUsers(model.getID()); //get user by ID
			isbnList = user.getIsbn();
			storeISBN.add(isbn);
		}

		Collections.sort(isbnList);
		isbnList = removeFromList(isbnList, storeISBN); //Update Person's ArrayList of ISBN
		user.getIsbn().removeAll(user.getIsbn()); //Remove all the original ISBN from Person Object 
		
		/* Update Person ISBN ArrayList */
		for(int i = 0; i < isbnList.size(); i++) {
			String newIsbn = isbnList.get(i);
			user.getIsbn().add(newIsbn);
		}	
	}

	/**
	 * Removes book based on ISBN. If params ISBN matches isbnTree ISBN, remove it from DataManager
	 * @param params - the ISBN from the book the user wants to sell
	 * @param isbnTree - the isbnTreeMap from DataManager
	 */
	private void removeBook(Map<String, Object> params, TreeMap<String, Book> isbnTree) {
		for(Map.Entry<String, Object> entry : params.entrySet()) {
			String isbn = entry.getKey();

			for(Map.Entry<String, Book> entry2 : isbnTree.entrySet()) {
				String isbn2 = entry2.getKey();

				if(isbn.equals(isbn2)) {
					isbnTree.remove(isbn);
					break;
				} else {
					continue;
				} 
			}
		}

	}

	/**
	 * Remove the ISBN that does not match the Data Manager isbn list
	 * @param isbnList - The isbn list from Person object
	 * @param storeISBN - the isbn list from Data Manager
	 * @return newIsbn - the new ISBN ArrayList
	 */
	private ArrayList<String> removeFromList(ArrayList<String> isbnList, ArrayList<String> storeISBN) 
	{
		ArrayList<String> newIsbn = new ArrayList<String>();
		for(int i = 0; i < isbnList.size(); i++) {
			for(int j = 0; j < storeISBN.size(); j++) {
				if(isbnList.get(i).equals(storeISBN.get(j))) {
					newIsbn.add(isbnList.get(i));
				} 
			}
		}
		return newIsbn;
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
