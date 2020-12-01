package Social.Exception;

public class ViolatedInvariantException extends RuntimeException {

    public ViolatedInvariantException (String cause) {
        super(cause);
    }
}
