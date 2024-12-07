package com.example.creditapp.controller;

import com.example.creditapp.dto.AuthenticationDTO;
import com.example.creditapp.model.User;
import com.example.creditapp.securite.JwtService;
import com.example.creditapp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@RequestMapping(path = "users")
public class userController {

    private static final Logger log = LoggerFactory.getLogger(userController.class);
    private final UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;


    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping(path = "/register", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping(path = "/login", consumes = APPLICATION_JSON_VALUE)
    public Map<String,String> login(@RequestBody AuthenticationDTO authenticationDTO) {
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password())
        );

        if (authenticate.isAuthenticated()){
            return this.jwtService.generate(authenticationDTO.username());
        }
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id); // Assurez-vous que l'ID est défini pour l'objet User à mettre à jour
        return userService.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
