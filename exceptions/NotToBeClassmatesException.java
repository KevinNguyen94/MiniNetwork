package exceptions;

public class NotToBeClassmatesException extends Exception {
    public NotToBeClassmatesException(String message){
        System.err.println(message);
    }
}
