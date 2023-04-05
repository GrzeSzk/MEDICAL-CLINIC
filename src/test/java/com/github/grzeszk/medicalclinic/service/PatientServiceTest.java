package com.github.grzeszk.medicalclinic.service;

import com.github.grzeszk.medicalclinic.domain.Patient;
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

import static org.mockito.ArgumentMatchers.anyString;

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

    private static Patient getNewPatient(String email){
        return Patient.builder()
                .lastName("Brown")
                .email(email)
                .build();
    }





}
