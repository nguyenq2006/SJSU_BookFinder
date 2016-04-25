package Controller;

import java.util.*;
public class FindBookControllerTester {
	public static void main(String[]args)
	{
		String id = "008806915";
		int int_userId = Integer.parseInt(id);
		long long_userId = Long.parseLong(id);
		ArrayList<String> isbn = new ArrayList<String>();
		isbn.add("9781118431115");
		isbn.add("9781118087886");
		isbn.add("9781119056447");
		
		Person newUser = new Person("Tipper", "Truong", int_userId, isbn);
		Book newBook = new Book("12345678901234", "Big Java Early Objects", "Cay Horstmann", 56.50,long_userId, false); 
		Book newBook2 = new Book("9876543210987", "Some Book", "Tipper Truong", 65.90, long_userId, false);
		Book newBook3 = new Book("9781119056447", "Some Book 2", "Tipper Truong", 90.88, long_userId, false);
		DataManager dm = DataManager.sharedInstance();
		
		dm.addUser(newUser);
		
		dm.addBook(newBook);
		dm.addBook(newBook2);
		dm.addBook(newBook3);
		
		dm.getBook(newBook.getIsbn());
		
		Object book1 = newBook;
		Object book2 = newBook2;
		Object book3 = newBook3;
		
		Object user = newUser;
		
		TreeMap<String, Object> fbcMap = new TreeMap<String, Object>();
		
		fbcMap.put("12345678901234", book1); //Book
		fbcMap.put("9876543210987", book2); //Book
		fbcMap.put("9781119056447", book3); //Book
		FindBookController fbc = new FindBookController(fbcMap);
		
		//Test 1
		System.out.println(fbc.matchBookTitle("Some Book"));	
	}
}

