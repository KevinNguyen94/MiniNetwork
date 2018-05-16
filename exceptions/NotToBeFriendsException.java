package exceptions;

public class NotToBeFriendsException extends Exception {
    public NotToBeFriendsException(String message){
        System.err.println(message);
    }
}
