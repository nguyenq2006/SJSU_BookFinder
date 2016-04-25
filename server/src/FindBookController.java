import java.util.*;

public class FindBookController {
	Book model;
	private ArrayList<Book> allBook = new ArrayList<Book>();
	Person user;
	String response = "";

	/**
	 * Retrieving User Information and Book Information from DataManager class
	 */
	public FindBookController(Map<String, Object> params) {

		DataManager dm = DataManager.sharedInstance();
		TreeMap<String, Book> isbnTreeMap = (TreeMap<String, Book>) dm.getIsbnTreeMap();
		
		//Loop through the keySet, if it matches the DataManager isbnTree.keySet, retrieve it.
		for(String isbn: params.keySet()) {
			if (params.keySet().equals(isbnTreeMap.keySet())) {
				model = dm.getBook(isbn);
				allBook.add(model);
			} else {
				System.out.println("This ISBN does not match the Book you are looking for");
				break;
			}
		}


		response = "Student ID: " + user.getId()  
				+ "*&#$&!@#"   + " " +
				"First Name: " + user.getfName() + " "
				+ "*&#$&!@#" + " " +
				"Last Name: " + user.getlName() + " "
				+ "*&#$&!@#" + " " +
				"ISBN:" + user.getIsbn() + " " + "\n" +
				"ISBN: " + model.getIsbn() 
				+ "*&#$&!@#"   + " " +
				"Book Title: " + model.getBookTitle() + " " 
				+ "*&#$&!@#" + " " +
				"Author: " + model.getAuthor() + " "
				+ "*&#$&!@#" + " " +
				"Price: " + model.getPrice() + " "
				+ "*&#$&!@#" + " " +
				"Student ID: " + model.getID() + " " 
				+ "*&#$&!@#" + " " + 
				"HardCover?: " + model.isHardCover() + " " + "\n";


		//Save book information to database
		//this.saveToDataBase(modelToString(this.model));
	}

	/**
	 * Get the book title based on what the user inputs
	 * @param userInput - the title that the user inputs
	 * @return the book that matches the title the user inputs
	 */
	public Book matchBookTitle(String userInput)
	{
		for(int i = 0; i < allBook.size(); i++)
		{
			String bookTitle = allBook.get(i).getBookTitle();
			if(userInput.equals(bookTitle)) {
				return allBook.get(i);
			}
		}
		return null;

	}

	/**
	 * Converting Book object to String object
	 * @param model - book info from Book class
	 * @return resultString - from Book object to String object
	 */

	public static String modelToBookString(Book m)
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
				"HardCover?: " + m.isHardCover() + " " + "\n" ;

		return resultString;
	}

	/**
	 * Converting String object to Book object
	 * @param bookInfo - the book information in String 
	 * @return bookObject - final conversion of String object -> Book object
	 **/
	public Book stringToBook(String bookInfo)
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

	
	public String getResponse(){
		return this.response;
	}


}
