package exceptions;

public class FileIsNotExistException extends Exception {
    public FileIsNotExistException(String message){
        super(message);
        System.err.println(message);
    }
}
