package ru.isu.webproject.kanplan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.isu.webproject.kanplan.model.*;
import ru.isu.webproject.kanplan.repository.AutoUserRepository;
import ru.isu.webproject.kanplan.security.JwtUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    AutoUserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        System.out.println("--->SignIp: "+loginRequest);

        AutoUser user = userRepository.findAutoUserByUsername(loginRequest.getUsername());
System.out.println("--->SignIp: "+user);
        if (user == null || !encoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Неверно введенный логин и/или пароль"));
        }
        
        System.out.println("---> Pass: Неверно введенный логин и/или пароль");

        Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(), 
                                loginRequest.getPassword()
                        )
        );
        
        System.out.println("---> Pass: new UsernamePasswordAuthenticationToken");

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
         System.out.println("---> Pass: SecurityContextHolder.getContext().setAuthentication(authentication)");
        String jwt = jwtUtils.generateJwtToken(authentication);
        System.out.println("---> Pass: String jwt = jwtUtils.generateJwtToken(authentication)");

        AutoUser userDetails = (AutoUser) authentication.getPrincipal();
        System.out.println("---> Pass:  AutoUser userDetails = (AutoUser) authentication.getPrincipal()");
        List<String> roles = userDetails.getAuthorities().stream()
                        .map(item -> item.getAuthority())
                        .collect(Collectors.toList());
        System.out.println("---> Pass:   List<String> roles = userDetails.getAuthorities().stream()");
        System.out.println("---> userDetails: " + userDetails.getId() + ", " + userDetails.getUsername() + ", " + userDetails.getMail() + ". jwt: " + jwt);
        return ResponseEntity.ok(
            new JwtResponse(
                jwt,
                  userDetails.getId(),
                userDetails.getUsername(), 
                userDetails.getMail(), 
                roles
            )
        );
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        System.out.println("--->SignUp: " + signUpRequest);
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Пользователь с данным логином существует"));
        }

        // Create new user's account
        AutoUser user = new AutoUser(
                signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()),
                   signUpRequest.getMail()
                        
        );

        user.setRole("ROLE_ADMIN");
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Пользователь успешно зарегистрирован"));
    }
}