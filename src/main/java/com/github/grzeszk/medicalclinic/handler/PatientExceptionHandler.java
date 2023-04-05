package com.github.grzeszk.medicalclinic.handler;

import com.github.grzeszk.medicalclinic.exception.IncorrectPatientDataException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PatientExceptionHandler {

    @ExceptionHandler(IncorrectPatientDataException.class)
    public ResponseEntity<String> incorrectPatientDataResponse(IncorrectPatientDataException incorrectPatientDataException){
        return ResponseEntity.status(400).body(incorrectPatientDataException.getMessage());
    }
}
