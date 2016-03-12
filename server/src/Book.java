/**
 * TIPPER SUCKs DICK.
 * 
 *
 */
public class Book 
{
	private String author;
	private String bookTitle;
	private String isbn;
	private double price;
	private long id;
	private boolean hardCover;
	
	/**
	 * construct a new object book
	 * @param isbn - isbn of the book
	 * @param title - title of the book
	 * @param author - author of the book
	 * @param price - selling price
	 * @param studentID - SJSU student id
	 * @param hardCover - true if the book is hardcover, or otherwise
	 */
	public Book(String isbn, String title, String author, double price, long studentID, boolean hardCover){
		this.author = author;
		this.isbn = isbn;
		this.bookTitle = title;
		this.price = price;
		this.hardCover = hardCover;
	}

	/**
	 * get the author of the book
	 * @return author of the book
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * get the title of the book
	 * @return the book title
	 */
	public String getBookTitle() {
		return bookTitle;
	}

	/**
	 * get the isbn
	 * @return isbn of the book
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * get the selling price
	 * @return price of the book
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * get the student ID
	 * @return student ID
	 */
	public long getID()
	{
		return id;
	}

	/**
	 * check if the book is hardcover or not
	 * @return true if the book is hardcover, or otherwise
	 */
	public boolean isHardCover() {
		return hardCover;
	}
	
	
}
