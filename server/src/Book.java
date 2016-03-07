
public class Book 
{
	private String author;
	private String bookTitle;
	private String isbn;
	private double price;
	private boolean hardCover;
	
	public Book(String isbn, String title, String author, double price, boolean hardCover)
	{
		this.author = author;
		this.isbn = isbn;
		this.bookTitle = title;
		this.price = price;
		this.hardCover = hardCover;
	}
	
	public Book(String isbn)
	{
		this(isbn, "", "", 0, false);
	}
	
}
