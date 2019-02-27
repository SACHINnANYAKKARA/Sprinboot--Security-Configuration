package com.hospital.jwtauthentication.controller;



import com.hospital.jwtauthentication.message.request.SignUpFormPatient;
import com.hospital.jwtauthentication.model.Patient;
import com.hospital.jwtauthentication.model.Role;
import com.hospital.jwtauthentication.model.RoleName;
import com.hospital.jwtauthentication.repository.PatientRepository;
import com.hospital.jwtauthentication.repository.RoleRepository;
import com.hospital.jwtauthentication.security.jwt.JwtProviderPatient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/patient")
public class AuthRestAPIsPatient {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PatientRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProviderPatient jwtProvider;



    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpFormPatient signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<String>("Fail -> Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<String>("Fail -> Email is already in use!",
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        Patient patient = new Patient(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(),signUpRequest.getTelephone(),signUpRequest.getAge(),signUpRequest.getBloodgroup(),encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch(role) {
                case "admin":
                    Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(adminRole);

                    break;
                case "pm":
                    Role pmRole = roleRepository.findByName(RoleName.ROLE_PM)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(pmRole);

                    break;
                default:
                    Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(userRole);
            }
        });

        patient.setRoles(roles);
        userRepository.save(patient);

        return ResponseEntity.ok().body("User registered successfully!");
    }
}
