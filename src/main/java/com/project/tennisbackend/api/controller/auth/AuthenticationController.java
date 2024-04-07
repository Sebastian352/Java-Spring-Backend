package com.project.tennisbackend.api.controller.auth;

import com.project.tennisbackend.api.model.LoginBody;
import com.project.tennisbackend.api.model.LoginResponse;
import com.project.tennisbackend.api.model.RegistrationBody;
import com.project.tennisbackend.exception.UserAlreadyExistsException;
import com.project.tennisbackend.model.LocalUser;
import com.project.tennisbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/auth")
public class AuthenticationController {

    private UserService userService;

    public AuthenticationController(UserService userService){
        this.userService = userService;
    }
    @CrossOrigin(origins = "*",allowedHeaders = "*")
    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody RegistrationBody registrationBody){
        try {
            userService.registerUser(registrationBody);
            return ResponseEntity.ok().build();
        }catch (UserAlreadyExistsException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @CrossOrigin(origins = "*",allowedHeaders = "*")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody){
        String jwt = userService.loginUser(loginBody);
        if(jwt == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            LoginResponse response = new LoginResponse();
            response.setJwt(jwt);
            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestBody RegistrationBody registrationBody) {
        this.userService.deleteUser(registrationBody);

    }

    @PostMapping("/update")
    public void updateUser(@RequestBody RegistrationBody registrationBody){
        this.userService.putUser(registrationBody);
    }

    @GetMapping("/get")
    public LocalUser getUser(@RequestBody RegistrationBody registrationBody){
        return this.userService.getUser(registrationBody);
    }

    @GetMapping("/users")
    public ResponseEntity<Iterable<LocalUser>> getAllUsers() {
        Iterable<LocalUser> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
