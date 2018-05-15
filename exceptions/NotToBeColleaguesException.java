package exceptions;

public class NotToBeColleaguesException extends Exception {
    public NotToBeColleaguesException(){
        System.err.println("You trying to connect a not-adult person in a colleague relation");
    }
}
