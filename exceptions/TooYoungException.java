package exceptions;

public class TooYoungException extends Exception {
    public TooYoungException(String message){
        super(message);
        System.err.println("The user seems putting a piece above an existing piece...");
    }
}
