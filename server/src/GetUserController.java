import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class GetUserController {
	private Person user;
	private String response = "";
	/**
	 * String - Student ID
	 * Object - Person Object
	 * @param params - the TreeMap 
	 */
	public GetUserController(Map<Long, Object> params)
	{
		DataManager dm = DataManager.sharedInstance();
		TreeMap<Long, Person> getUsersDM = dm.getUsersMap();

		for(Map.Entry<Long, Object> entry : params.entrySet()) {
			long id1 = entry.getKey();
			for(Map.Entry<Long, Person> entry2 : getUsersDM.entrySet()) {
				long id2 = entry2.getKey();
				if(id1 == id2) {
					user = dm.getUsers(id1);
				} 
			}
		
		}
		
		//this.saveToDataBase(modelToString(this.user));
	}
	

	/**
	 * Saving information to DatabaseManager
	 * @param modelString - the model to save to database
	 */
	private void saveToDataBase(String modelString){
		// DataManager dm = DataManager.sharedInstance();

	}

	public Person getUser(){
		return user;
	}

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
			int pt2 = userLine.indexOf(']');
			if(userLine.contains("[")) {
				//Get ArrayList
				String theISBN = userLine.substring(pt1+1, pt2).trim();
				isbn.add(theISBN);
			}



		} in.close();



		Person p = new Person(fName, lName, id, isbn);

		return p;
	}


	public String getResponse(){
		return this.response;
	}

}


