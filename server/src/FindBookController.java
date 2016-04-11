import java.util.*;

public class FindBookController extends HttpRequestController{
	
	String response;

	public FindBookController(Map<String, Object> params){
		DataManager ddb = DataManager.sharedInstance();
		Book testBook = ddb.getBook("91919191911");
		response = testBook.getBookTitle();
	}


	public String getResponse(){
		return this.response;
	}
}