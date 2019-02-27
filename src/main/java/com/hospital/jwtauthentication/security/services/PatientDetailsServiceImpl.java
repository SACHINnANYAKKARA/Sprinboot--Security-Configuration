package com.hospital.jwtauthentication.security.services;

import com.hospital.jwtauthentication.model.Patient;
import com.hospital.jwtauthentication.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class PatientDetailsServiceImpl implements UserDetailsService {

    @Autowired
    PatientRepository patientRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Patient patient = patientRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("")
                );

        return PatientPrinciple.build(patient);
    }
}