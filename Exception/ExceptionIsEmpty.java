package Exception;

public class ExceptionIsEmpty extends Exception {
    public ExceptionIsEmpty() {
        super();
    }

    public ExceptionIsEmpty(String msg){
        super(msg);
    }
}