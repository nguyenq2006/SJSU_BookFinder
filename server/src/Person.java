import java.util.*;
/**
 * 
 * Creating a Person class for Students at SJSU
 *
 */
public class Person {
	
	private String fName;
	private String lName;
	private int id;
	private ArrayList<String> isbn;
	

	
	/**
	 * @param name - The name of the user
	 * @param id - the id of the user
	 * @param isbn - The isbn of the book
	 */
	public Person(String firstName, String lastName, int theId, ArrayList<String>theISBN) {
		fName = firstName;
		lName = lastName;
		id = theId;
		isbn = theISBN;
	}

	/** Gets the first name of the user
	 * @return fName - first name of the user
	 */
	public String getfName() {
		return fName;
	}
	
	/**
	 * Gets the last name of the user
	 * @return lName - last name of the user
	 */
	public String getlName() {
		return lName;
	}
	
	/**
	 * Setting the first name of user
	 * @param fName - First name of user
	 */
	public void setfName(String fName) {
		this.fName = fName;
	}

	/**
	 * Setting the last name of user
	 * @param lName - last name of user
	 */
	public void setlName(String lName) {
		this.lName = lName;
	}

	/**
	 * Get Student ID from the user
	 * @return id - the id of user
	 */
	public int getId() {
		return id;
	}

	/**
	 * Get list of ISBN from user
	 * @return - isbn - the list of ISBN from suer
	 */
	public ArrayList<String> getIsbn() {
		return isbn;
	}
	
	
	
	
	

}