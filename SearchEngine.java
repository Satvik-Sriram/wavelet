import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> queries = new ArrayList<>(); 

    public String handleRequest(URI url) {
        if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                queries.add(parameters[1]);
                return String.format("Query Added");
            }
        } else if (url.getPath().contains("/search")) {
            int temp = 0;
            String[] parameters = url.getQuery().split("=");
            String[] search = new String[queries.length];
            if(parameters[0].equals("s")) {
                for(int i = 0; i < queries.length; i++) {
                    if(queries.get(i).contains(parameters[1])) {
                        search[temp] = queries.get(i);
                        temp++;
                    }
                }
                return String.format(search);
            }
        }
        return "404 Not Found!";
        
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
