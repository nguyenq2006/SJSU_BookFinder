import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class GetUserController {
	private String response = "";
	
	public GetUserController(Map<String, Object> params) {
		DataManager dm = DataManager.sharedInstance();
		long studentId = Long.parseLong(params.get("id")+"");
		Person p = dm.getUsers(studentId);
		response += p.getfName() + ", " +  p.getlName();
	}

	public String getResponse() {
		return this.response;
	}

}
