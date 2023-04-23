package com.github.grzeszk.medicalclinic.service;

import com.github.grzeszk.medicalclinic.domain.Patient;
import com.github.grzeszk.medicalclinic.exception.IncorrectPatientDataException;
import com.github.grzeszk.medicalclinic.repository.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
    @Mock
    PatientRepository patientRepository;

    @InjectMocks
    PatientService patientService;

    @Test
    //methodName_StateUnderControl_ExpectedResult
    void getAllPatients_correctDataProvided_patientsReturn(){
        List<Patient> patients = new LinkedList<>();
        patients.add(getNewPatient("123"));
        patients.add(getNewPatient("321"));
        Mockito.when(patientRepository.getAllPatients())
                .thenReturn(patients);

        List<Patient> result = patientService.getAllPatients();

        Assertions.assertEquals(2, result.size());
    }

    @Test
    void getPatientByEmail_correctEmailProvided_patientReturned(){
        Patient patient = getNewPatient("1234");
        Mockito.when(patientRepository.getPatientByEmail(anyString()))
                .thenReturn(Optional.of(patient));

        Patient result = patientService.getPatientByEmail("1234");

        Assertions.assertEquals("1234", result.getEmail());
    }

    @Test
    void addPatient_correctPatientDataProvided_patientAdded(){
        Patient patient = getNewPatient("email1");
        Mockito.when(patientRepository.getPatientByEmail(eq(patient.getEmail())))
                .thenReturn(Optional.empty());
        Mockito.when(patientRepository.addPatient(any()))
                .thenReturn(patient);

        Patient result = patientService.addPatient(patient);

        Assertions.assertEquals("email1", result.getEmail());
    }

    @Test
    void deletePatientByEmail_patientExists_patientDeleted(){
        Patient patient = getNewPatient("email2");
        Mockito.when(patientRepository.getPatientByEmail(eq(patient.getEmail())))
                .thenReturn(Optional.of(patient));
        Mockito.when(patientRepository.deletePatientByEmail(eq(patient)))
                .thenReturn(patient);

        Patient result = patientService.deletePatientByEmail("email2");

        Assertions.assertEquals("email2", result.getEmail());
    }

    @Test
    void updatePatientByEmail_patientExists_patientUpdated(){
        Patient patientBeforeUpdate = getNewPatient("email3");
        Patient patientAfterUpdate = getNewPatient("email4");
        Mockito.when(patientRepository.getPatientByEmail(eq(patientBeforeUpdate.getEmail())))
                .thenReturn(Optional.of(patientBeforeUpdate));
        Mockito.when(patientRepository.updatePatient(eq(patientBeforeUpdate),eq(patientAfterUpdate)))
                .thenReturn(patientAfterUpdate);

        Patient result = patientService.updatePatientByEmail("email3", patientAfterUpdate);

        Assertions.assertEquals("email4", result.getEmail());
    }

    @Test
    void updatePatientPassword_patientExists_patientUpdated(){
        Patient patientBeforeUpdate = getNewPatient("email5");
        Patient patientAfterUpdate = getNewPatient("email5");
        patientAfterUpdate.setPassword("12345");
        Mockito.when(patientRepository.getPatientByEmail(eq(patientBeforeUpdate.getEmail())))
                .thenReturn(Optional.of(patientBeforeUpdate));
        Mockito.when(patientRepository.updatePatientPassword(eq(patientBeforeUpdate), eq("12345")))
                .thenReturn(patientAfterUpdate);

        Patient result = patientService.updatePatientPassword("email5", "12345");

        Assertions.assertEquals("12345", result.getPassword());
    }

    @Test
    void addPatient_patientDoesNotExist_exceptionThrown() {
        Patient patient = getNewPatient("email6");
        Mockito.when(patientRepository.getPatientByEmail(anyString()))
                .thenReturn(Optional.of(patient));

        IncorrectPatientDataException result = Assertions.assertThrows(IncorrectPatientDataException.class,
                ()-> patientService.addPatient(patient));

        Assertions.assertEquals("Patient already exists", result.getMessage());
    }

    @Test
    void deletePatientByEmail_patientDoesNotExist_exceptionThrown() {
        Mockito.when(patientRepository.getPatientByEmail(anyString()))
                .thenReturn(Optional.empty());

        IncorrectPatientDataException result = Assertions.assertThrows(IncorrectPatientDataException.class,
                ()-> patientService.deletePatientByEmail(""));

        Assertions.assertEquals("Patient not found", result.getMessage());
    }
    @Test
    void updatePatientByEmail_patientDoesNotExist_exceptionThrown() {
        Mockito.when(patientRepository.getPatientByEmail(anyString()))
                .thenReturn(Optional.empty());

        IncorrectPatientDataException result = Assertions.assertThrows(IncorrectPatientDataException.class,
                ()-> patientService.updatePatientByEmail("", null));

        Assertions.assertEquals("Patient not found", result.getMessage());
    }
    @Test
    void updatePatientPassword_patientDoesNotExist_exceptionThrown(){
        Mockito.when(patientRepository.getPatientByEmail(anyString()))
                .thenReturn(Optional.empty());

        IncorrectPatientDataException result = Assertions.assertThrows(IncorrectPatientDataException.class,
                ()-> patientService.updatePatientPassword("", ""));

        Assertions.assertEquals("Incorrect Patient data", result.getMessage());
    }

    private static Patient getNewPatient(String email){
        return Patient.builder()
                .lastName("Brown")
                .email(email)
                .build();
    }
}
