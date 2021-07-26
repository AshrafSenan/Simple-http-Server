
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the server that support 5 threads.
 *
 * @author Someone
 */
public class Server {

    private ServerSocket ss; // listen for client connection requests on this server socket;
    private ExecutorService pool; // Pool of threads
    private String folder; // the webiste folder.
    private final int maxThreads = 5; // Max threads

    /**
     * Build a server serve from some folder on the specific port.
     *
     * @param folder the folder to serve from
     * @param port the port to work on
     */
    public Server(String folder, int port) {
        try {
            ss = new ServerSocket(port); // get the socket.
            this.folder = folder; // get the folder
            pool = Executors.newFixedThreadPool(maxThreads); // create the threading pool
            System.out.println("Server started ... listening on port " + port + " ...");
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Start the server.
     */
    public void start() {
        try {

            while (true) {
                Socket conn = ss.accept(); // waif for aconnection
                // create new threaded connection handler
                ConnectionHandler ch = new ConnectionHandler(conn, folder);
                // add the thread to the connection handler
                pool.execute(ch);
                System.out.println("Server got new connection request from "
                        + conn.getInetAddress());
            }
        } catch (IOException ioe) {
            System.out.println("Ooops " + ioe.getMessage());
        }
    }
}
