import java.util.*;
import java.lang.Class;


public class NewUserController extends HttpRequestController{
	Person model;
	private String response = "";

	/**
	 * Taking a new user information and save it to database
	 * @param params - fName, lName, id, and isbn info
	 */
	@SuppressWarnings("unchecked")
	public NewUserController(Map<String, Object> params){
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
	 */
	private void saveToDataBase(Person model){
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
				"Student ID: " + m.getId()
				+ "*&#$&!@#"   + " " +
				"First Name: " + m.getfName() + " "
				+ "*&#$&!@#" + " " + "\n" +
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
	
	
	public static Person stringToModel (String userInfo)
	{
		//Convert String to Person Object
		Scanner in = new Scanner(userInfo);
		String info = "";

		String firstName = "";
		String lastName = "";
		String id = "";
		int studentID = 0;
		String isbn = "";
		ArrayList<String> theISBN = new ArrayList<String>();

		while(in.hasNextLine())
		{
			String line = in.nextLine().trim();
			int colon = line.lastIndexOf(':');

			if(line.contains("First Name: "))
			{
				firstName = line.substring(colon+1, line.length()).trim();
			}

			else if (line.contains("Last Name: "))
			{
				lastName = line.substring(colon+1, line.length()).trim();

			}

			else {
				info = line.substring(colon+1, line.length()).trim();
				for(int i = 0 ; i < info.length(); i++)
				{
					if(Character.isDigit(info.charAt(i)) && !info.startsWith("["))
					{
						id = info;
						studentID = Integer.parseInt(id);
						break;
					} 

					else if (info.startsWith("[") && info.endsWith("]"))
					{
						isbn = info.substring(1, info.length()-1);
						theISBN.add(isbn);
						break;
					} 

				}
			}
		}
		
		Person p = new Person(firstName, lastName, studentID, theISBN);
		return p;
	}


	public String getResponse(){
		return this.response;
	}
}

