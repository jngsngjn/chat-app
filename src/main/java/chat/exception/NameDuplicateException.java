package chat.exception;

public class NameDuplicateException extends RuntimeException {

    public NameDuplicateException() {
    }

    public NameDuplicateException(String message) {
        super(message);
    }
}