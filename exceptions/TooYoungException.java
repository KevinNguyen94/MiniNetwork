package exceptions;

public class TooYoungException extends Exception {
    public TooYoungException(){
        System.err.println("The selected person is too young!!");
    }
}
