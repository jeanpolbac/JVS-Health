package com.example.jvshealth.service;

import com.example.jvshealth.exception.InformationExistException;
import com.example.jvshealth.models.Doctor;
import com.example.jvshealth.repository.DoctorRepository;
import com.example.jvshealth.security.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    public static Doctor findDoctorByEmailAddress;
    private final  DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public Doctor findUserByEmailAddress(String emailAddress){
        return doctorRepository.findDoctorByEmailAddress(emailAddress);
    }

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, @Lazy PasswordEncoder passwordEncoder,
                       JWTUtils jwtUtils,
                       @Lazy AuthenticationManager authenticationManager) {
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    public Doctor createUser(Doctor doctorObject) {
        if (!doctorRepository.existsByEmailAddress(doctorObject.getEmailAddress())) {
            doctorObject.setPassword(passwordEncoder.encode(doctorObject.getPassword()));
            return doctorRepository.save(doctorObject);
        } else {
            throw new InformationExistException("user with email address " + doctorObject.getEmailAddress() + " already exists");
        }
    }

}
