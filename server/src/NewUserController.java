import java.util.*;
import java.io.FileNotFoundException;
import java.lang.Class;


public class NewUserController extends HttpRequestController{
	Person model;
	private String response = "";

	/**
	 * Taking a new user information and save it to database
	 * @param params - fName, lName, id, and isbn info
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public NewUserController(Map<String, Object> params) throws FileNotFoundException{
		String firstName = (String) params.get("firstname");
		String lastName = (String) params.get("lastname");
		int id =  Integer.parseInt((String) params.get("id"));
		ArrayList<String> isbn = new ArrayList<String>();
		model = new Person(firstName, lastName, id, isbn);
		// this.saveToDataBase(modelToString(this.model));
		this.saveToDataBase(this.model);
	}

	/**
	 * Saving information to DatabaseManager
	 * @param modelString - the model to save to database
	 * @throws FileNotFoundException 
	 */
	private void saveToDataBase(Person model) throws FileNotFoundException{
		DataManager dbm = DataManager.sharedInstance();
		dbm.addUser(model);
		// if sucess(){}
		response = "New user: " + model.getfName() +" with id: " + model.getId() + " created!";
	}



	/**
	 *  Retrieving Student ID from database
	 * @param id - the student id 
	 * @return the user's id
	 */
	/*public static Person getPersonWithId(int id)
	{
		DatabaseManager dbm = DatabaseManager.sharedInstance();
		String personString = dbm.getUserWithId(id);
		Person x = stringToModel(personString);
		return x;
	}  */

	/**
	 * Converting Person object to String object
	 * Format: 123456789 *&#$&!@# john *&#$&!@# smith *&#$&!@# 1234567891234 , 1234567891234 , 1234567891234
	 * 
	 * Student ID -> *&#$&!@# -> First Name -> *&#$&!@# -> LastName -> *&#$&!@# -> ISBN numbers from User
	 * 
	 * @param m - user information from the Person class
	 * @return resultString - from Person object to String object
	 */
	
	
	public static String modelToString(Person m){
		String resultString = 
				"Student ID: " + m.getId() + " "
				+ "*&#$&!@#"   + " " +
				"First Name: " + m.getfName() + " "
				+ "*&#$&!@#" + " " +
				"Last Name: " + m.getlName() + " "
				+ "*&#$&!@#" + " " +
				"ISBN:" + m.getIsbn() + " " + "\n";
		return resultString;
	}

	/**
	 * Retrieving the String information from Person info and converting it to Person Object
	 * @param userInfo - the user information in String
	 * @return p - final conversion of String object -> Person object
	 */
	public static Person stringToModel(String userInfo)
	{
		Scanner in = new Scanner(userInfo);
		

		String studentID = "";
		int id = 0;
		String fName = "";
		String lName = "";
		ArrayList<String> isbn = new ArrayList<String>();
		
		while(in.hasNextLine())
		{
			String userLine = in.nextLine().trim();
			
			int colon = userLine.indexOf(':');
			int star = userLine.indexOf('*');
			if(userLine.contains("Student ID: ")) {
				//student ID
				studentID = userLine.substring(colon+1, star).trim();
				id = Integer.parseInt(studentID);
				userLine = userLine.replace(studentID, "");
				userLine = userLine.replace("Student ID:", "");
				userLine = userLine.replace("*&#$&!@# Fi", "").trim();
			}
			
			
			
			int colon1 = userLine.indexOf(':');
			int star1 = userLine.indexOf('*');
			if(userLine.contains("rst Name:")) {
				//first name
				fName = userLine.substring(colon1+1, star1).trim();
				
				userLine = userLine.replace("rst Name: ", "");
				userLine = userLine.replace(fName, "");
				userLine = userLine.replace("*&#$&!@# L", "").trim();
				
			}
			
			
			int colon2 = userLine.indexOf(':');
			int star2 = userLine.indexOf('*');
			if(userLine.contains("ast Name:")) {
				//last name
				lName = userLine.substring(colon2+1, star2).trim();
				
				userLine = userLine.replace("ast Name: ", "");
				userLine = userLine.replace(lName, "");
				userLine = userLine.replace("*&#$&!@# ISBN:", "");
				
			}
			
			int pt1 = userLine.indexOf('[');
			if(userLine.contains("[")) {
				//Get ArrayList
				String theISBN = userLine.substring(2, userLine.length()-1).trim();
				isbn.add(theISBN);
				
			}
			
			
			 
		}
		


		Person p = new Person(fName, lName, id, isbn);
		
		return p;
	}


	public String getResponse(){
		return this.response;
	}
}

