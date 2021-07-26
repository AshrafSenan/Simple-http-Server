

import java.io.IOException;
import java.net.Socket;

/**
 * This class is a threaded handler handle clients requests and send the
 * appropriate responses.
 *
 * @author Someone
 */
public class ConnectionHandler implements Runnable {

    private Socket conn; // Socket representino TCP/IP connection to Client
    private String serverFolder; // the website that was set to the server to work on

    /**
     * Create new threaded connection handler to serve the client individually.
     *
     * @param conn is the client socket
     * @param folder the web site serverFolder received from the main.
     */
    public ConnectionHandler(java.net.Socket conn, String folder) {

        this.conn = conn;
        this.serverFolder = folder;
    }

    /**
     * Overriding the runnable run to run the server functions.
     */
    @Override
    public void run() {
        System.out.println("New ConnectionHanlder constructed ....");
        try {
            serveClient(); // Serve the client
        } catch (DisconnectedException | IOException ex) {
            FileUtil.log("Error in surveyting the client", ex.getMessage(), "404 Not FOund");
        }

    }

    /**
     * Read the client request and send appropriate response or an error status
     * code and error page.
     *
     *
     * @throws DisconnectedException if the client is already disconnected
     * @throws IOException if couldn't read files
     */
    public void serveClient() throws DisconnectedException, IOException {
        try {
            // create http request to read the request from the socket
            MyHttpRequest httpRequest;
            httpRequest = new MyHttpRequest(this.conn);
            httpRequest.readRequest(); // read the request
            // Create a new response for the current request
            // using the connection socket and from a specific website folder
            MyHttpResponse httpResponse = new MyHttpResponse(httpRequest, this.conn,
                    serverFolder);
            // Prepare the response components
            httpResponse.prepareResponse();
            // Send the response to the browser
            httpResponse.sendResponse();
            // log the response
            FileUtil.log(httpRequest.getMethode(), httpRequest.getPath(), httpResponse.getStatus());
            cleanup();
        } catch (IOException e) {
            System.out.println("print Client Data: " + e.getMessage());
        }
    }

    /**
     * Close the connection.
     */
    public void cleanup() {
        System.out.println("ConnectionHanlder: ... cleaning up and exiting");
        try {
            conn.close();
        } catch (IOException ioe) {
            System.out.println("ConnectionHanlder:cleanup " + ioe.getMessage());
        }
    }

}
