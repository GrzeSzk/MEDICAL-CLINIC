package com.github.grzeszk.medicalclinic.exception;


public class IncorrectPatientDataException extends RuntimeException {
    public IncorrectPatientDataException(String message){
        super(message);
    }

    public IncorrectPatientDataException(){
        super("Incorrect Patient data");
    }
}
