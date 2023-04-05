package com.github.grzeszk.medicalclinic.controller;

import com.github.grzeszk.medicalclinic.domain.Patient;
import com.github.grzeszk.medicalclinic.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/patients")
@RestController
@AllArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public List<Patient> getPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("{email}")
    public Patient getPatientByEmail(@PathVariable String email) {
        return patientService.getPatientByEmail(email);
    }

    @PostMapping
    public Patient addPatient(@RequestBody Patient patient) {
        return patientService.addPatient(patient);
    }

    @DeleteMapping({"{email}"})
    public Patient deletePatientByEmail(@PathVariable String email) {
        return patientService.deletePatientByEmail(email);
    }

    @PutMapping({"{email}"})
    public Patient updatePatientByEmail(@PathVariable String email, @RequestBody Patient patient) {
        return patientService.updatePatientByEmail(email, patient);
    }

    @PatchMapping ("{email}")
    public Patient updatePatientPassword(@PathVariable String email, @RequestBody String password) {
        return patientService.updatePatientPassword(email, password);
    }
}
