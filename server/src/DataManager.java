import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
//singleton class to manage the database
public class DataManager{
	private static DataManager dm = new DataManager();
	private TreeMap<String, Book> isbnTree;
	private BookTree titleTree;
	private TreeMap<Long, Person> users;

	/**
	 * private constructor for DataManager
	 * @throws FileNotFoundException 
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
	 * @throws FileNotFoundException 
	 */
	public void addUser(Person newUser) throws FileNotFoundException{
		long userID = newUser.getId();
		if(!users.containsKey(userID)){
			users.put(userID, newUser);
		}
		save();
	}

	/**
	 * add new book to the database
	 * @param newBook - new Book object
	 * @param userID - the user ID
	 * @throws FileNotFoundException 
	 */
	public void addBook(Book newBook) throws FileNotFoundException{
		//add new book to the user
		Person user = users.get(newBook.getID());
		user.addBook(newBook);

		//add  new book to the database
		if(!isbnTree.containsKey(newBook.getIsbn())){
			isbnTree.put(newBook.getIsbn(), newBook);
			titleTree.insert(newBook);
		}
		save();
	}

	/**
	 * get book by ISBN number
	 * @param isbn - book's ISBN number
	 * @return book object
	 */
	public Book getBook(String isbn){
		return isbnTree.get(isbn);
	}
	
	/**
	 * Returns the IsbnTree TreeMAp
	 * @return isbnTree
	 */
	public TreeMap<String, Book> getIsbnTreeMap() {
		return isbnTree;
	}

	/**
	 * save all the data to a text file
	 * @throws FileNotFoundException
	 */
	public void save() throws FileNotFoundException{
		PrintStream out = new PrintStream("users_data.txt");
		for(long id : users.keySet()){
			Person p = users.get(id);
			out.print(NewUserController.modelToString(p));;
		}

		out = new PrintStream("books_data.txt");
		for(String isbn : isbnTree.keySet()){
			Book b = isbnTree.get(isbn);
			out.print(AddBookController.modelToString(b));
		}
	}

	public void load() throws FileNotFoundException{
		Scanner in = new Scanner(new File("src/users_data.txt"));
		while(in.hasNextLine()){
			String line = in.nextLine();
			if(!line. equals("")){
				Person p = NewUserController.stringToModel(line);
				addUser(p);
			}
		}

		in = new Scanner(new File("src/books_data.txt"));
		while(in.hasNextLine()){
			String line = in.nextLine();
			if(!line. equals("")){
				Book b = AddBookController.stringToModel(line);
				addBook(b);
			}
		}
	}
}
