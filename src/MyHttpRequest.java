
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * This class reads the the HTTP request that the browser sent and decompose it
 * to its main components.
 *
 * @author Someone
 */
public class MyHttpRequest {

    private Socket client; // handle the client connection socket.
    private String request; // read the full request into the request.
    private String[] requestsLines; // all request lines
    // split the first line in the request, METHOD path protocol/version
    private String[] requestFirstLine;
    private String httpMethod; // http method
    private String requestPath; // the requested path.
    private String httpVersion; // the protocol/version.
    private String httpHost; // the host read it from the request.
    private String bodyData; // in case of POST, read the post request body line here.

    /**
     * Build the request.
     *
     * @param s is the socket
     */
    public MyHttpRequest(Socket s) {
        this.client = s;
    }

    /**
     * Get the request data.
     *
     * @return request data.
     */
    public String getRequestData() {
        return request;
    }

    /**
     * Get the post body data.
     *
     * @return the data.
     */
    public String getBodyData() {
        return bodyData;
    }

    /**
     * This function return the path that the client requested.
     *
     * @return the URL path
     */
    public String getPath() {
        return this.requestPath;
    }

    /**
     * Returns back the HTTP method that the client request.
     *
     * @return the HTTP method
     */
    public String getMethode() {
        return this.httpMethod;
    }

    /**
     * get the host.
     *
     * @return the host string
     */
    public String getHost() {
        return this.httpHost;
    }

    /**
     * Return the protocol and version HTTP/1.1 for example.
     *
     * @return a string contains the protocol version.
     */
    public String getVersion() {
        return this.httpVersion;
    }

    /**
     * Read the HTTP request and discompose it to its components.
     *
     * @throws IOException in case reading failed
     */
    public void readRequest() throws IOException {
        BufferedReader br; // buffered line to read the request
        // get the socket input stream
        br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        StringBuilder requestBuilder = new StringBuilder();
        String line; // read the rquest line by line and append them to the request string.
        while (!(line = br.readLine()).isEmpty()) { // read all the lines
            requestBuilder.append(line).append("\r\n");
        }

        this.request = requestBuilder.toString();
        requestsLines = request.split("\r\n"); // split all lines
        requestFirstLine = requestsLines[0].split(" "); // get the first line
        httpMethod = requestFirstLine[0]; // get the HTTP method
        // if the http method is post,
        // then read the data line which sent by the browse.
        if (httpMethod.equals("POST")) {
            bodyData = br.readLine() + "\r\n";
        }
        requestPath = requestFirstLine[1]; // get the requested path the second word in the first line
        httpVersion = requestFirstLine[2]; // get the protocol version
        httpHost = requestsLines[1].split(" ")[1]; // get the host

    }
}
