package exceptions;

public class TooYoungException extends Exception {
    public TooYoungException(String message){
        super(message);
        System.err.println("The selected person is too young!!");
    }
}
