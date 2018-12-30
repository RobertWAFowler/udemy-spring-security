package com.oreilly.security.web.controllers;

import com.oreilly.security.domain.entities.Appointment;
import com.oreilly.security.domain.entities.AutoUser;
import com.oreilly.security.domain.repositories.AppointmentRepository;
import com.oreilly.security.domain.repositories.AutoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller()
@RequestMapping("/appointments")
public class AppointmentController {


    private final AppointmentRepository appointmentRepository;
    private final AutoUserRepository autoUserRepository;

    @Autowired
    public AppointmentController(AppointmentRepository appointmentRepository, AutoUserRepository autoUserRepository) {
        this.appointmentRepository = appointmentRepository;
        this.autoUserRepository = autoUserRepository;
    }

    @ModelAttribute
    public Appointment getAppointment(){
        return new Appointment();
    }

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String getAppointmentPage(){
        return "appointments";
    }

    @ResponseBody
    @RequestMapping(value="/save", method=RequestMethod.POST)
    public List<Appointment> saveAppointment(@ModelAttribute Appointment appointment){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AutoUser user = autoUserRepository.findByUsername(username);

        appointment.setUser(user);
        appointment.setStatus("Initial");
        appointmentRepository.save(appointment);
        return this.appointmentRepository.findAll();
    }

    @ResponseBody
    @RequestMapping("/all")
    public List<Appointment> getAppointments(){
        return this.appointmentRepository.findAll();
    }

    @RequestMapping("/{appointmentId}")
    public String getAppointment(@PathVariable("appointmentId") Long appointmentId, Model model){
        Appointment appointment = appointmentRepository.findOne(appointmentId);
        model.addAttribute("appointment", appointment);
        return "appointment";
    }

}
