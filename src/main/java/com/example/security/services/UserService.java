package com.example.security.services;

import com.example.security.dto.UserRequest;
import com.example.security.dto.UserResponse;
import com.example.security.models.User;
import com.example.security.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    final UserRepository repository;
    final ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse insert(UserRequest userRequest) {
        User userInsert = modelMapper.map(userRequest, User.class);
        userInsert.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User userInserted = repository.save(userInsert);
        return modelMapper.map(userInserted, UserResponse.class);
    }
}
