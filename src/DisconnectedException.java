
/**
 * Disconnect Exception in case the client is no longer connected.
 *
 * @author Someone
 */
public class DisconnectedException extends Exception {

    /**
     * Build a new exception.
     *
     * @param message the exception message.
     */
    public DisconnectedException(String message) {
        super(message);
    }
}
