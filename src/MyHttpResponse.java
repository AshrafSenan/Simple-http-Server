
import java.io.IOException;

import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Path;

/**
 * This class write the suitable response to the client based on the request.
 *
 * @author Someone
 */
public class MyHttpResponse {

    private MyHttpRequest request; // the http request that we will respone to.
    private Socket client; // the client socket.
    private String status; // the http response status.
    private String fileType; // the response file-type
    private byte[] fileContent; // the file content
    private String serverFolder; // the website folder.

    /**
     * Create a new HTTP response.
     *
     * @param request the request
     * @param client the client socket
     * @param serverFolder the web site folder
     */
    public MyHttpResponse(MyHttpRequest request, Socket client, String serverFolder) {
        this.request = request;
        this.client = client;
        this.serverFolder = serverFolder;
    }

    /**
     * Prepare the response fields.
     */
    public void prepareResponse() {
        // if the methode is GET or HEAD
        if (request.getMethode().toUpperCase().equals("GET")
                || request.getMethode().toUpperCase().equals("HEAD")) {
            // Create the path of the requested file
            Path filePath = FileUtil.getFilePath(serverFolder, request.getPath());
            // if the file is exist
            if (FileUtil.isFileExist(filePath)) {
                try {
                    status = "200 OK"; // HTTP status OK
                    fileType = FileUtil.getFileType(filePath); // get the File type
                    //if(request.getMethode().toUpperCase().equals("GET")) // if the request is get
                    fileContent = FileUtil.readFileBytes(filePath); // get the File content
                } catch (IOException ioe) {
                    System.out.println("... Prepare Response issue: " + ioe.getMessage());
                }
            } else { // if the file is not exist
                status = "404 Not Found"; // ERROR 404, file not found
                fileType = "text/html"; // return a problem page
                fileContent = ("UNEXPECTED ERROR 404 the File  " + request.getPath()
                        + "  NOT FOUND").getBytes();
            }
            // if the methode is post
        } else if (request.getMethode().toUpperCase().equals("POST")) {
            FileUtil.writePostData(request.getBodyData()); // save the post data on server
            status = "201 Created"; // response code OK
            fileType = "text/javascript"; // the file content is text
            fileContent = ("Succesfully Saved!").
                    getBytes();
        } else { // if the methode is not implemented
            status = "501 Not Implemented"; // response status not implemented
            fileType = "text/html"; // return an error page
            fileContent = ("UNEXPECTED ERROR 501 THE METHOD  " + request.getMethode()
                    + "  NOT Implemented").getBytes();
        }
    }

    /**
     * Write the prepared response to the client.
     *
     * @throws java.io.IOException in case failed in writing to client.
     */
    public void sendResponse() throws IOException {
        // get the output stream to write
        OutputStream clientOutput = client.getOutputStream();
        // write the response header
        clientOutput.write(("HTTP/1.1 " + status + "\r\n").getBytes());
        clientOutput.write(("Content-Type: " + fileType + "\r\n").getBytes());
        clientOutput.write(("Content-Length: " + fileContent.length + "\r\n").getBytes());
        clientOutput.write("\r\n".getBytes());
        // if the methode is not HEAD write the file content to the response body
        if (!request.getMethode().toUpperCase().equals("HEAD")) {
            clientOutput.write(fileContent);
            clientOutput.write("\r\n\r\n".getBytes());
        }
        clientOutput.flush();
        client.close();
    }
    /**
     * return the response status.
     *
     * @return the status
     */
    public String getStatus() {
        return this.status;
    }

}
