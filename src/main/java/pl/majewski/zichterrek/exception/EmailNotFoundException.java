package pl.majewski.zichterrek.exception;

public class EmailNotFoundException extends Exception{
    public EmailNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
