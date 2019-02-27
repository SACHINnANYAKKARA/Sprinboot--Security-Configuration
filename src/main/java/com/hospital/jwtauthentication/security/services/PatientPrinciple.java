package com.hospital.jwtauthentication.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hospital.jwtauthentication.model.Patient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PatientPrinciple implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String username;

    private String email;


    private String telephone;


    private String age;

    private String bloodgroup;

    @JsonIgnore
    private String password;



    private Collection<? extends GrantedAuthority> authorities;

    public PatientPrinciple(Long id, String name,
                         String username, String email,String telephone,String age,String bloodgroup, String password,
                         Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.telephone =telephone;
        this.age =age;
        this.bloodgroup=bloodgroup;
        this.password = password;
        this.authorities = authorities;
    }

    public static PatientPrinciple build(Patient patient) {
        List<GrantedAuthority> authorities = patient.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());

        return new PatientPrinciple(
                patient.getId(),
                patient.getName(),
                patient.getUsername(),
                patient.getEmail(),
                patient.getTelephone(),
                patient.getAge(),
                patient.getBloodgroup(),
                patient.getPassword(),
                authorities
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAge() {
        return age;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

     @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PatientPrinciple patient = (PatientPrinciple) o;
        return Objects.equals(id, patient.id);
    }



}