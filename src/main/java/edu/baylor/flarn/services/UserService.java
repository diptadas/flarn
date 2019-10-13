package edu.baylor.flarn.services;

import edu.baylor.flarn.models.User;
import edu.baylor.flarn.models.UserType;
import edu.baylor.flarn.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public List<User> getUserByType(UserType userType){
        return userRepository.findByUserType(userType);
    }

    public Optional<User> getUser(long Id){
        return userRepository.findById(Id);
    }

    public void changeUserType(long Id, UserType userType){
        Optional<User> optional  = userRepository.findById(Id);
        optional .ifPresent(user -> {
            if(!user.getUserType().equals(userType)){
                user.setUserType(userType);
                userRepository.changeUserType(user.getId(),userType);
            }

        });
    }

}
