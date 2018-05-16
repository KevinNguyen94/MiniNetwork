package exceptions;

public class TooYoungException extends Exception {
    public TooYoungException(){
        System.err.println("Error!!! You trying to make friend with a young child!");
    }
}
