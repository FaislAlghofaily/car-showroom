package com.example.car_showroom.service.impl;


import com.example.car_showroom.dto.user.CreateUserDTO;
import com.example.car_showroom.dto.user.LoginResponseDTO;
import com.example.car_showroom.dto.user.LoginUserDTO;
import com.example.car_showroom.entity.User;
import com.example.car_showroom.repository.UserRepository;
import com.example.car_showroom.service.AuthenticationService;
import com.example.car_showroom.util.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public ResponseEntity<User> signup(CreateUserDTO input) {
        User user = new User();
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setFullName(input.getFullName());
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<LoginResponseDTO> login(String acceptedLanguage, LoginUserDTO loginUserDTO) {
        String jwtToken = jwtHelper.generateToken(authenticate(loginUserDTO));
        return new ResponseEntity(new LoginResponseDTO(jwtToken, jwtHelper.getExpirationTime()), HttpStatus.OK);
    }

    private User authenticate(LoginUserDTO input) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                input.getEmail(),
                input.getPassword()));
        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }
}