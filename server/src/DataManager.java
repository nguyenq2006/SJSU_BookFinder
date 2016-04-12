import java.util.*;
//singleton class to manage the database
public class DataManager {
	private static DataManager dm = new DataManager();
	private TreeMap<String, Book> isbnTree;
	private BookTree titleTree;
	private TreeMap<Long, Person> users;
	
	/**
	 * private constructor for DataManager
	 */
	private DataManager(){
		this.isbnTree = new TreeMap<String, Book>();
		this.titleTree = new BookTree();
		this.users = new TreeMap<Long, Person>();
	}
	
	/**
	 * get instance of the DataManager
	 * @return the object of DataManager
	 */
	public static DataManager sharedInstance(){
		return dm;
		
	}
	
	/**
	 * add new user to the database
	 * @param newUser - a Person object(new user)
	 */
	public void addUser(Person newUser){
		long userID = newUser.getId();
		if(!users.containsKey(userID)){
			users.put(userID, newUser);
		}
	}
	
	/**
	 * add new book to the database
	 * @param newBook - new Book object
	 * @param userID - the user ID
	 */
	public void addBook(Book newBook){
		//add new book to the user
		Person user = users.get(newBook.getID());
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
