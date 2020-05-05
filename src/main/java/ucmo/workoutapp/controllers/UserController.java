package ucmo.workoutapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ucmo.workoutapp.entities.Client;
import ucmo.workoutapp.entities.User;
import ucmo.workoutapp.exceptions.MapValidationErrorService;
import ucmo.workoutapp.payload.JWTLoginSuccessResponse;
import ucmo.workoutapp.payload.LoginRequest;
import ucmo.workoutapp.security.JwtTokenProvider;
import ucmo.workoutapp.services.ClientService;
import ucmo.workoutapp.services.UserService;
import ucmo.workoutapp.validator.UserValidator;

import javax.validation.Valid;
import java.security.Principal;

import static ucmo.workoutapp.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private ClientService clientService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    // @route   POST api/users/login
    // @desc    Login a user
    // @access  Public
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
    }

    // @route   POST api/users/register
    // @desc    Register a user
    // @access  Public
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        // Validate passwords match
        userValidator.validate(user, result);

        User newUser = userService.saveUser(user);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }


    @PostMapping("/client")
    public ResponseEntity<?> createClient(@Valid @RequestBody Client client, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        Client newClient = clientService.createOrUpdateClient(client, principal.getName());
        return new ResponseEntity<>(newClient, HttpStatus.OK);
    }

//    @PostMapping("/register")
//    public @ResponseBody
//    User create(@RequestParam String username, @RequestParam String password) {
//        Role authority = new Role(2);
//        Set<Role> authorities = new HashSet<Role>();
//        authorities.add(authority);
//
//        User user = new User(username,bCryptPasswordEncoder.encode(password),true,authorities);
//        return userRepository.save(user);
//    }
//
//    @GetMapping("/addUser")
//    public String addNewProctor() {
//        return "addUser";
//    }
//
//    @GetMapping("/getUsers")
//    public @ResponseBody List<User> getProctors() {
//        return userRepository.findByRoles_Id(2);
//    }
//
//    @GetMapping("/all")
//    public @ResponseBody List<User> getAllUsers(){
//        return userRepository.findAll();
//    }

}
