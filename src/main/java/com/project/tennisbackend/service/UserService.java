package com.project.tennisbackend.service;

import com.project.tennisbackend.api.model.LoginBody;
import com.project.tennisbackend.api.model.RegistrationBody;
import com.project.tennisbackend.exception.UserAlreadyExistsException;
import com.project.tennisbackend.model.LocalUser;
import com.project.tennisbackend.model.dao.LocalUserDAO;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class UserService {
    private LocalUserDAO localUserDAO;
    private  EncryptionService encryptionService;
    private JWTService jwtService;
    public UserService(LocalUserDAO localUserDAO, EncryptionService encryptionService, JWTService jwtService) {
        this.localUserDAO = localUserDAO;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
    }

    public void deleteUser(RegistrationBody registrationBody) {
        LocalUser user = new LocalUser();
        user = getUser(registrationBody);
        localUserDAO.delete(user);
    }

    public LocalUser putUser(RegistrationBody registrationBody) {
        LocalUser user = getUser(registrationBody);
        if(user != null) {
            user.setUsername(registrationBody.getUsername());
            user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        }
        return localUserDAO.save(user);
    }



    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException {
        if (localUserDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()){
            throw new UserAlreadyExistsException();
        }
        LocalUser user = new LocalUser();
        user.setUsername(registrationBody.getUsername());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword())) ;
        return localUserDAO.save(user);
    }

    public String loginUser(LoginBody loginBody){
        Optional<LocalUser> opUser = localUserDAO.findByUsernameIgnoreCase(loginBody.getUsername());
        if(opUser.isPresent()){
            LocalUser user = opUser.get();
            if(encryptionService.verifyPassword(loginBody.getPassword(),user.getPassword())){
                return jwtService.generateJWT(user);
            }
        }
        return null;
    }

    public LocalUser getUser(RegistrationBody registrationBody) {
        LocalUser user = new LocalUser();
        if(localUserDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()){
            user = localUserDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).get();
        }else {
            user = null;
        }
        return user;
    }
}
