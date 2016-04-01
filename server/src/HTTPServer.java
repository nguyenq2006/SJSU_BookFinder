import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HTTPServer {

	private static String response = "";

	public static void startServer() throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(9999), 0);
		
		RequestHandler requestHandler = new RequestHandler();

		HttpContext context = server.createContext("/a", requestHandler);
		context.getFilters().add(new ParameterFilter());
		server.start();
	}

	static class RequestHandler implements HttpHandler {
		 @Override
		    public void handle(HttpExchange exchange) throws IOException {
		        @SuppressWarnings("unchecked")
		        //localhost:9999/a?requesttype=newuser&firstname=john&lastname=smith&id=009558549
				Map<String, Object> params =
		           (Map<String, Object>)exchange.getAttribute("parameters");

		        String requestType = "";
		        if (params.containsKey("requesttype")) {
		        	requestType = (String) params.get("requesttype");
		        	params.remove("requesttype");
		        }

		        switch(requestType){
		        	case "newuser":
		        		System.out.println("Request Type is new user");

		        		NewUserController newUser = new NewUserController(params);
		        		response = newUser.getResponse();
		        		break;

		        	case "addbook":
		        		System.out.println("Request Type is add book");

		        		AddBookController addBook = new AddBookController(params);
		        		response = "Your books have been added to the database!";
		        		break;

		        	case "sellbook":
		        		System.out.println("Request Type is sell book");

		        		SellBookController sellBook = new SellBookController(params);
		        		response = "Your book has been sold";
		        		break;

		        	case "findbook":
		        		System.out.println("Request Type is find book");

		        		FindBookController findBook = new FindBookController(params);
		        		response = "Here is your book: ";
		        		break;

		        	default:
		        		System.out.println("Request Type is undefined");
		        }

		        for(Object key: params.keySet()){
		        	//System.out.println(key + ":" + params.get(key));
		        }

	            exchange.sendResponseHeaders(200, response.length());
	            OutputStream os = exchange.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
		    }
	}
	
	static class ParameterFilter extends Filter {

	    @Override
	    public String description() {
	        return "Parses the requested URI for parameters";
	    }

	    @Override
	    public void doFilter(HttpExchange exchange, Chain chain)
	        throws IOException {
	        parseGetParameters(exchange);
	        parsePostParameters(exchange);
	        chain.doFilter(exchange);
	    }    

	    private void parseGetParameters(HttpExchange exchange)
	        throws UnsupportedEncodingException {

	        Map<String, Object> parameters = new HashMap<String, Object>();
	        URI requestedUri = exchange.getRequestURI();
	        String query = requestedUri.getRawQuery();
	        parseQuery(query, parameters);
	        exchange.setAttribute("parameters", parameters);
	    }

	    private void parsePostParameters(HttpExchange exchange)
	        throws IOException {

	        if ("post".equalsIgnoreCase(exchange.getRequestMethod())) {
	            @SuppressWarnings("unchecked")
	            Map<String, Object> parameters =
	                (Map<String, Object>)exchange.getAttribute("parameters");
	            InputStreamReader isr =
	                new InputStreamReader(exchange.getRequestBody(),"utf-8");
	            BufferedReader br = new BufferedReader(isr);
	            String query = br.readLine();
	            parseQuery(query, parameters);
	        }
	    }

	     @SuppressWarnings("unchecked")
	     private void parseQuery(String query, Map<String, Object> parameters)
	         throws UnsupportedEncodingException {

	         if (query != null) {
	             String pairs[] = query.split("[&]");

	             for (String pair : pairs) {
	                 String param[] = pair.split("[=]");

	                 String key = null;
	                 String value = null;
	                 if (param.length > 0) {
	                     key = URLDecoder.decode(param[0],
	                         System.getProperty("file.encoding"));
	                 }

	                 if (param.length > 1) {
	                     value = URLDecoder.decode(param[1],
	                         System.getProperty("file.encoding"));
	                 }

	                 if (parameters.containsKey(key)) {
	                     Object obj = parameters.get(key);
	                     if(obj instanceof List<?>) {
	                         List<String> values = (List<String>)obj;
	                         values.add(value);
	                     } else if(obj instanceof String) {
	                         List<String> values = new ArrayList<String>();
	                         values.add((String)obj);
	                         values.add(value);
	                         parameters.put(key, values);
	                     }
	                 } else {
	                     parameters.put(key, value);
	                 }
	             }
	         }
	    }
	}

}