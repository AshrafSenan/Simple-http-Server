
/**
 * Create the server and run it.
 *
 * @author Some one
 */
public class WebServerMain {

    /**
     * The main function.
     *
     * @param args main arguments.
     */
    public static void main(String[] args) {
        // check the number of parameters, if < 2 show error message.
        if (args.length < 2) {
            System.out.println("Usage: java WebServerMain <document_root> <port>");
        } else {
            int port = 0; // the connection port specified byt the user
            try {
                port = Integer.parseInt(args[1]);  // try to read the port
            } catch (NumberFormatException nfe) {
                System.out.println("The port should be an integer!");
            }
            if (port <= 0) { // if the port is wrong show error message
                System.out.println("Port number must be greted than 0");
            } else { // start the server from the specif folder on port
                System.out.println("Working Directory = " + System.getProperty("user.dir"));
                Server s = new Server(args[0], port);  // create the server
                s.start(); // start the server
            }
        }
    }
}
