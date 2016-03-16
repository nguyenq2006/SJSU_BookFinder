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

	public static void startServer() throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(9999), 0);
		HttpContext context = server.createContext("/test", new MyHttpHandler());
		server.createContext("/newuser", new NewUserHttpHandler());
		server.createContext("/findbook", new FindBookHttpHandler());
		server.createContext("/addbook", new AddBookHttpHandler());
		server.createContext("/sellbook", new SellBookHttpHandler());
		context.getFilters().add(new ParameterFilter());
		server.start();
	}

	static class MyHttpHandler implements HttpHandler {
		 @Override
		    public void handle(HttpExchange exchange) throws IOException {
		        @SuppressWarnings("unchecked")
		        //http://localhost:9999/test?name=john&bookid=13784794535
				Map<String, Object> params =
		           (Map<String, Object>)exchange.getAttribute("parameters");
		        
		        for(Object key: params.keySet()){
		        	System.out.println(key + ":" + params.get(key));
		        }
	            String response = "This is the response";
	            exchange.sendResponseHeaders(200, response.length());
	            OutputStream os = exchange.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
		    }
	}

	static class NewUserHttpHandler implements HttpHandler{
		@Override
		    public void handle(HttpExchange exchange) throws IOException {
		        @SuppressWarnings("unchecked")
				Map<String, Object> params =
		           (Map<String, Object>)exchange.getAttribute("parameters");
		        
		        for(Object key: params.keySet()){
		        	System.out.println(key + ":" + params.get(key));
		        }
	            String response = "New user created!";
	            exchange.sendResponseHeaders(200, response.length());
	            OutputStream os = exchange.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
		    }
	}

	static class FindBookHttpHandler implements HttpHandler{
		@Override
		    public void handle(HttpExchange exchange) throws IOException {
		        @SuppressWarnings("unchecked")
				Map<String, Object> params =
		           (Map<String, Object>)exchange.getAttribute("parameters");
		        
		        for(Object key: params.keySet()){
		        	System.out.println(key + ":" + params.get(key));
		        }
	            String response = "Here is your book: {Sample book}";
	            exchange.sendResponseHeaders(200, response.length());
	            OutputStream os = exchange.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
		    }
	}

	static class AddBookHttpHandler implements HttpHandler{
		@Override
		    public void handle(HttpExchange exchange) throws IOException {
		        @SuppressWarnings("unchecked")
				Map<String, Object> params =
		           (Map<String, Object>)exchange.getAttribute("parameters");
		        
		        for(Object key: params.keySet()){
		        	System.out.println(key + ":" + params.get(key));
		        }
	            String response = "Your book has been added";
	            exchange.sendResponseHeaders(200, response.length());
	            OutputStream os = exchange.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
		    }
	}

	static class SellBookHttpHandler implements HttpHandler{
		@Override
		    public void handle(HttpExchange exchange) throws IOException {
		        @SuppressWarnings("unchecked")
				Map<String, Object> params =
		           (Map<String, Object>)exchange.getAttribute("parameters");
		        
		        for(Object key: params.keySet()){
		        	System.out.println(key + ":" + params.get(key));
		        }
	            String response = "Your book has been sold to: {John Smith}";
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