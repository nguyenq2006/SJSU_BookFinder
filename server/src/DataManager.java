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
	 * @param newUser a Person object(new user)
	 * @param first true if it's a initial load
	 * @throws FileNotFoundException 
	 */
	public void addUser(Person newUser, boolean first) throws FileNotFoundException{
		long userID = newUser.getId();
		if(!users.containsKey(userID)){
			users.put(userID, newUser);
		}
		if(!first) save();
	}

	/**
	 * add new book to the database
	 * @param newBook new Book object
	 * @param first true if it's a initial load
	 * @throws FileNotFoundException 
	 */
	public void addBook(Book newBook, boolean first) throws FileNotFoundException{
		//add new book to the user
		Person user = users.get(newBook.getID());
		user.addBook(newBook);

		//add  new book to the database
		if(!isbnTree.containsKey(newBook.getIsbn())){
			isbnTree.put(newBook.getIsbn(), newBook);
			titleTree.insert(newBook);
		}
		if(!first) save();
	}

	/**
	 * get book by ISBN number
	 * @param isbn  book's ISBN number
	 * @return book object
	 */
	public Book getBookISBN(String isbn){
		return isbnTree.get(isbn);
	}
	
	/**
	 * Get user by Student ID Number
	 * @param studentId - Student ID
	 * @return Person object
	 */
	public Person getUsers(Long studentId) {
		return users.get(studentId);
	}
	
	/**
	 * get book by book title	
	 * @param book title of the book	
	 * @return the book
	 */
	public Book getBook(String book){
		String isbn = titleTree.getBook(book);
		return isbnTree.get(isbn);
	}
	
	/**
	 * remove the book that has been sold
	 * @param isbn book ISBN
	 */
	public void remove(String isbn){
		Book soldBook = isbnTree.get(isbn);
		Person seller = users.get(soldBook.getID());
		seller.removeBook(isbn);
		isbnTree.remove(isbn);
		titleTree.delete(isbnTree.get(isbn));
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
		out.close();
		PrintStream out2 = new PrintStream("books_data.txt");
		for(String isbn : isbnTree.keySet()){
			Book b = isbnTree.get(isbn);
			out2.print(AddBookController.modelToString(b));
		}
		out2.close();
	}

	/**
	 * load the data from a text file
	 * @throws FileNotFoundException if the file not found
	 */
	public void load(){
		Scanner in = new Scanner(System.in);
		try {
			 in = new Scanner(new File("users_data.txt"));
		} catch (FileNotFoundException e) {
			// TODO: handle exception
//			try {
//				in = new Scanner(new File("server/users_data.txt"));
//			} catch (Exception e2) {
//				//oh well
//			}
		}
		while(in.hasNextLine()){
			String line = in.nextLine();
			if(!line. equals("")){
				Person p = NewUserController.stringToModel(line);
				try {
					addUser(p, true);
				} catch (Exception e) {
					System.err.println("addUser exception occured");
				}
				
			}
		}
		in.close();
		Scanner in2 = new Scanner(System.in);
		try {
			in2 = new Scanner(new File("books_data.txt"));
		} catch (FileNotFoundException e) {
//			try {
//				in2 = new Scanner(new File("server/books_data.txt"));
//			} catch (Exception e2) {
//				//too bad
//			}
		}
		while(in2.hasNextLine()){
			String line = in2.nextLine();
			if(!line. equals("")){
				Book b = AddBookController.stringToModel(line);
				try {
					addBook(b, true);
				} catch (Exception e) {
					// TODO: handle exception
					System.err.println("addBook exception occured");
				}
				
			}
		}
		in2.close();
	}
}
