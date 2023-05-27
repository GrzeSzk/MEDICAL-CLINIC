package com.github.grzeszk.medicalclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.grzeszk.medicalclinic.domain.Patient;
import com.github.grzeszk.medicalclinic.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientRepository patientRepository;

    @BeforeEach
    void setup() {
        Optional<Patient> patient = patientRepository.getPatientByEmail("test1");
        if (patient.isEmpty()) {
            patientRepository.addPatient(createPatient());
        }
    }

    @Test
    void getAllPatients() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/patients"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void getPatientByEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/patients/test1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test1"));
    }

    @Test
    void addPatient() throws Exception {
        Patient patient = Patient.builder()
                .email("test2")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patient)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test2"));
    }

    @Test
    void updatePatientByEmail() throws Exception {
        Patient patientAfterUpdate = Patient.builder()
                .email("test3")
                .lastName("Brown")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/patients/test1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patientAfterUpdate)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Brown"));
    }

    @Test
    void updatePatientPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/patients/test1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("123456"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("123456"));
    }

    @Test
    void deletePatientByEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/patients/test1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test1"));
    }

    public static Patient createPatient(){
        return Patient.builder()
                .email("test1")
                .build();
    }
}
