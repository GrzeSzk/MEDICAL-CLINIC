package com.github.grzeszk.medicalclinic.repository;

import com.github.grzeszk.medicalclinic.domain.Patient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PatientRepository {

    private List<Patient> patients;

    public List<Patient> getAllPatients() {
        return patients;
    }

    public Optional<Patient> getPatientByEmail(String email) {
        return patients.stream()
                .filter(patient -> patient.getEmail().equals(email))
                .findFirst();
    }

    public Patient addPatient(Patient patient) {
        patients.add(patient);
        return patient;
    }

    public Patient deletePatientByEmail(Patient patient) {
        patients.remove(patient);
        return patient;
    }

    public Patient updatePatient(Patient patient, Patient patientEditInfo) {
        patient.setEmail(patientEditInfo.getEmail());
        patient.setPassword(patientEditInfo.getPassword());
        patient.setBirthday(patientEditInfo.getBirthday());
        patient.setFirstName(patientEditInfo.getFirstName());
        patient.setLastName(patientEditInfo.getLastName());
        patient.setIdCardNo(patientEditInfo.getIdCardNo());
        patient.setPhoneNumber(patientEditInfo.getPhoneNumber());
        return patient;
    }

    public Patient updatePatientPassword(Patient patient, String password) {
        patient.setPassword(password);
        return patient;
    }
}
