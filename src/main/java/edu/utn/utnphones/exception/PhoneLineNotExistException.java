package edu.utn.utnphones.exception;

public class PhoneLineNotExistException extends Exception{

    public PhoneLineNotExistException(String line) {
        super(line);
    }

}
