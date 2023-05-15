package com.example.healtcaremanagement.controller;

import com.example.healtcaremanagement.entity.Doctor;
import com.example.healtcaremanagement.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/doctors")
    public String doctorsPage(ModelMap modelMap) {
        List<Doctor> all = doctorRepository.findAll();
        modelMap.addAttribute("doctors", all);
        return "doctors";
    }

    @GetMapping("/doctors/add")
    public String addDoctorPage(){
        return "addDoctor";
    }

    @PostMapping("/doctors/add")
    public String addDoctor(@RequestParam("name") String name, String surname,
                            String email, String specialty, int phoneNumber, String profilePic){
        Doctor doctor = new Doctor();
        doctor.setName(name);
        doctor.setSurname(surname);
        doctor.setEmail(email);
        doctor.setSpecialty(specialty);
        doctor.setPhoneNumber(phoneNumber);
        doctor.setProfilePic(profilePic);
        doctorRepository.save(doctor);
        return "redirect:/doctors";
    }

    @GetMapping("/doctors/remove")
    public String removeDoctor(@RequestParam("id") int id) {
        doctorRepository.deleteById(id);
        return "redirect:/doctors";
    }
}
