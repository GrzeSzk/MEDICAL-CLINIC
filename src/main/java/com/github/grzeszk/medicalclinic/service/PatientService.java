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
        if (patient.getIdCardNo() == null) {
            throw new IncorrectPatientDataException("ID card number can not be empty!");
        }
        if (patient.getPassword().length() < 3) {
            throw new IncorrectPatientDataException("Password must have more than 3 characters");
        }
        return patientRepository.addPatient(patient);
    }

    public Patient deletePatientByEmail(String email) {
        Patient patient = patientRepository.getPatientByEmail(email)
                .orElseThrow(() -> new IncorrectPatientDataException("Patient not found"));
        return patientRepository.deletePatientByEmail(patient);
    }

    public Patient updatePatientByEmail(String email, Patient patientEditInfo) {
        validatePatientEditData(patientEditInfo);
        Patient patient = patientRepository.getPatientByEmail(email)
                .orElseThrow(() -> new IncorrectPatientDataException("Patient not found"));
        if (!patient.getIdCardNo().equals(patientEditInfo.getIdCardNo())) {
            throw new IncorrectPatientDataException("Patient can't change ID card number");
        }
        return patientRepository.updatePatient(patient, patientEditInfo);
    }

    public Patient updatePatientPassword(String email, String password) {
        Patient patient = patientRepository.getPatientByEmail(email)
                .orElseThrow(IncorrectPatientDataException::new);
        if (password == null || password.length() < 3) {
            throw new IncorrectPatientDataException("Password is empty or has got less than 3 characters");
        }
        return patientRepository.updatePatientPassword(patient, password);
    }

    public void validatePatientEditData(Patient patientEditInfo) {
        if (patientEditInfo.getPassword() == null || patientEditInfo.getEmail() == null || patientEditInfo.getBirthday() == null
                || patientEditInfo.getPhoneNumber() == null || patientEditInfo.getFirstName() == null
                || patientEditInfo.getLastName() == null || patientEditInfo.getIdCardNo() == null
                || patientEditInfo.getPassword().length() < 3) {
            throw new IncorrectPatientDataException("Invalid patient data");
        }
    }
}
