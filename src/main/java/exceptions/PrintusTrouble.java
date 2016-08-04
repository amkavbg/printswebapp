package exceptions;

/**
 * Created by tokido on 19.07.2016.
 */
public class PrintusTrouble extends RuntimeException {

    public PrintusTrouble(String s) {
        super(s);
    }

    public PrintusTrouble(String s, Throwable throwable){
        super(s, throwable);
    }

    public PrintusTrouble(Throwable throwable) {
        super(throwable);
    }
}
