package com.example.healtcaremanagement.controller;

import com.example.healtcaremanagement.entity.Appointment;
import com.example.healtcaremanagement.entity.Doctor;
import com.example.healtcaremanagement.entity.Patient;
import com.example.healtcaremanagement.repository.AppointmentRepository;
import com.example.healtcaremanagement.repository.DoctorRepository;
import com.example.healtcaremanagement.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Value("${healtcaremanagement.upload.image.path}")
    private String imageUploadPath;

    @GetMapping
    public String patientsPage(ModelMap modelMap) {
        List<Patient> all = patientRepository.findAll();
        modelMap.addAttribute("patients", all);
        return "patients";
    }

    @GetMapping("/{id}")
    public String singlePatientPage(@PathVariable("id") int id,
                                    ModelMap modelMap) {
        Optional<Patient> byId = patientRepository.findById(id);
        if (byId.isPresent()) {
            Patient patient = byId.get();
            List<Appointment> appointments = appointmentRepository.findAllByPatient_Id(patient.getId());
            modelMap.addAttribute("patient", patient);
            modelMap.addAttribute("appointments", appointments);
            return "singlePatient";
        } else {
            return "redirect:/patients";
        }
    }

    @GetMapping("/add")
    public String patientsAddPage(ModelMap modelMap) {
        List<Doctor> all = doctorRepository.findAll();
        modelMap.addAttribute("doctors", all);
        return "addPatient";
    }

    @PostMapping("/add")
    public String patientsAdd(@ModelAttribute Patient patient,
                              @RequestParam("image")MultipartFile multipartFile) throws IOException {
        if(multipartFile != null && !multipartFile.isEmpty()){
            String fileName = System.nanoTime() + "_" + multipartFile.getOriginalFilename();
            File file = new File(imageUploadPath + fileName);
            multipartFile.transferTo(file);
            patient.setImgName(fileName);
        }
        patientRepository.save(patient);
        return "redirect:/patients";
    }

    @GetMapping("/remove")
    public String removeDoctor(@RequestParam("id") int id) {
        patientRepository.deleteById(id);
        return "redirect:/patients";
    }

    @PostMapping("/appointment/add")
    public String addAppointment(@ModelAttribute Appointment appointment) {
        appointmentRepository.save(appointment);
        return "redirect:/patients/" + appointment.getPatient().getId();
    }
}
