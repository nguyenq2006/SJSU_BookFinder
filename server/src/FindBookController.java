import java.util.*;

public class FindBookController extends HttpRequestController{
	
	String response;

	public FindBookController(Map<String, Object> params){
		response = "12232";

	}

	public String getResponse(){
		return this.response;
	}
}