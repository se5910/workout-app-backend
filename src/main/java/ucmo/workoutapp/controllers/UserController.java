package ucmo.workoutapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.Role;
import ucmo.workoutapp.entities.User;
import ucmo.workoutapp.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    public @ResponseBody
    User create(@RequestParam String username, @RequestParam String password) {
        Role authority = new Role(2);
        Set<Role> authorities = new HashSet<Role>();
        authorities.add(authority);

        User user = new User(username,bCryptPasswordEncoder.encode(password),true,authorities);
        return userRepository.save(user);
    }

    @GetMapping("/addUser")
    public String addNewProctor() {
        return "addUser";
    }

    @GetMapping("/getUsers")
    public @ResponseBody List<User> getProctors() {
        return userRepository.findByRoles_Id(2);
    }

    @GetMapping("/all")
    public @ResponseBody List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
