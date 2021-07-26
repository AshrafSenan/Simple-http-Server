
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * FileUtil class will do all the functions needed from file system, reading
 * files, return file type, and write logs and save post data.
 *
 * @author Someone
 */
public class FileUtil {

    private static String logPath = "log.txt"; // log file path.
    private static String postPath = "post.txt"; // Post data file path.

    /**
     * Get the path for the file that is required by HTTP.
     *
     * @param folder the web site folder.
     * @param path the file path/name.
     * @return a Path to the specified file.
     */
    public static Path getFilePath(String folder, String path) {
        if ("/".equals(path)) { // if the user opened the webiste withous specifing file
            path = "/index.html"; // make the path the main webpage.
        }
        return Paths.get(folder, path); // return the file path.
    }

    /**
     * Return the file type of a specific file.
     *
     * @param filePath the file we need to know its type.
     * @return the file type
     * @throws IOException in case file not found
     */
    public static String getFileType(Path filePath) throws IOException {
        return Files.probeContentType(filePath);
    }

    /**
     * return wither the file is exist or not.
     *
     * @param path a file we need to check if exist
     * @return is the file exist
     */
    public static boolean isFileExist(Path path) {
        return Files.exists(path);
    }

    /**
     * Read the Bytes of a specific file.
     *
     * @param path a path of the file we want to read.
     * @return the File content bytes.
     * @throws IOException in case could not read the file
     */
    public static byte[] readFileBytes(Path path) throws IOException {
        return Files.readAllBytes(path);
    }

    /**
     * Write specific data to the log.
     *
     * @param reqType the HTTP request type.
     * @param reqPath the requested url.
     * @param resCode the response code.
     */
    public static void log(String reqType, String reqPath, String resCode) {
        try {
            // open the log file for append.
            FileWriter fw = new FileWriter(logPath, true);
            // create buffered writer to write data into the file.
            BufferedWriter bw = new BufferedWriter(fw);
            // write the data to the file.
            bw.write("Time :" + LocalDateTime.now().toString() + "\t Request Type:"
                    + reqType + "\t Request Path:" + reqPath + " \t Response Code:"
                    + resCode + "\n");
            // create new line.
            bw.newLine();
            bw.close();
        } catch (IOException oie) {
            System.out.println("... Error in Logging the information " + oie);
        }
    }

    /**
     * Write the post data the was received from the browser.
     *
     * @param data the post data.
     */
    public static void writePostData(String data) {
        try {
            // open the post data file for append.
            FileWriter fw = new FileWriter(postPath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            // Append the data.
            bw.write("Time :" + LocalDateTime.now().toString() + "\t Post Data: "
                    + data + "\n");
            bw.close();
        } catch (IOException oie) {
            System.out.println("... Error in Saving the Post Data ... " + oie);
        }
    }
}
