import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * A Controller to get the user information
 */
public class GetUserController {
	private String response = "";
	
	/**
	 * Construct a way to retrieve user information
	 * @param params the map that retrieves user information by ID
	 */
	public GetUserController(Map<String, Object> params) {
		DataManager dm = DataManager.sharedInstance();
		long studentId = Long.parseLong(params.get("id")+"");
		Person p = dm.getUsers(studentId);
		response += p.getfName() + ", " +  p.getlName();
	}

	/**
	 * Gets the response of the Person object
	 * @return response the info of the Person object
	 */
	public String getResponse() {
		return this.response;
	}

}
