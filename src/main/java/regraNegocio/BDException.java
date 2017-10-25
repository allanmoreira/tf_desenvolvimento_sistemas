package regraNegocio;

public class BDException extends Exception {
    public BDException(String message) {
        super(message);
    }

    public BDException() {
        super();
    }
}
