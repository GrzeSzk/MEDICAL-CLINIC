package com.github.grzeszk.medicalclinic.service;

import com.github.grzeszk.medicalclinic.domain.Patient;
import com.github.grzeszk.medicalclinic.exception.IncorrectPatientDataException;
import com.github.grzeszk.medicalclinic.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.getAllPatients();
    }

    public Patient getPatientByEmail(String email) {
        return patientRepository.getPatientByEmail(email)
                .orElseThrow(() -> new IncorrectPatientDataException("Patient not found"));
    }

    public Patient addPatient(Patient patient) {
        Optional<Patient> entity = patientRepository.getPatientByEmail(patient.getEmail());
        if (entity.isPresent()) {
            throw new IncorrectPatientDataException("Patient already exists");
        }
        return patientRepository.addPatient(patient);
    }

    public Patient deletePatientByEmail(String email) {
        Patient patient = patientRepository.getPatientByEmail(email)
                .orElseThrow(() -> new IncorrectPatientDataException("Patient not found"));
        return patientRepository.deletePatientByEmail(patient);
    }

    public Patient updatePatientByEmail(String email, Patient patientEditInfo) {
        Patient patient = patientRepository.getPatientByEmail(email)
                .orElseThrow(() -> new IncorrectPatientDataException("Patient not found"));
        return patientRepository.updatePatient(patient, patientEditInfo);
    }

    public Patient updatePatientPassword(String email, String password) {
        Patient patient = patientRepository.getPatientByEmail(email)
                .orElseThrow(() -> new IncorrectPatientDataException());
        return patientRepository.updatePatientPassword(patient, password);
    }
}
