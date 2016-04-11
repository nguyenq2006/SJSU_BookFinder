import java.util.*;

public class DataManager {
	private static DataManager dm = new DataManager();
	private TreeMap<String, Book> isbnTree;
	private BookTree titleTree;
	private TreeMap<Integer, Person> users;
	
	/**
	 * private constructor for DataManager
	 */
	private DataManager(){
		this.isbnTree = new TreeMap<String, Book>();
		this.titleTree = new BookTree();
		this.users = new TreeMap<Integer, Person>();
	}
	
	/**
	 * get instance of the DataManager
	 * @return the object of DataManager
	 */
	public static DataManager getIntance(){
		return dm;
	}
	
	/**
	 * add new user to the database
	 * @param newUser - a Person object(new user)
	 */
	public void addUser(Person newUser){
		int userID = newUser.getId();
		if(!users.containsKey(userID)){
			users.put(userID, newUser);
		}
	}
	
	/**
	 * add new book to the database
	 * @param newBook - new Book object
	 * @param userID - the user ID
	 */
	public void addBook(Book newBook, int userID){
		//add new book to the user
		Person user = users.get(userID);
		user.addBook(newBook);
		
		//add  new book to the database
		if(!isbnTree.containsKey(newBook.getIsbn())){
			isbnTree.put(newBook.getIsbn(), newBook);
			titleTree.insert(newBook);
		}
	}
	
	/**
	 * get book by ISBN number
	 * @param isbn - book's ISBN number
	 * @return book object
	 */
	public Book getBook(String isbn){
		return isbnTree.get(isbn);
	}
}
