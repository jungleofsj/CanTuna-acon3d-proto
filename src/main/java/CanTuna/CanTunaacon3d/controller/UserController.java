package CanTuna.CanTunaacon3d.controller;

import CanTuna.CanTunaacon3d.Service.UserService;
import CanTuna.CanTunaacon3d.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/new")
    public String createForm() {
        return "/createUserForm";
    }

    @PostMapping("/users/new")
    public ResponseEntity createUser(@RequestBody User user) {
        if (userService.joinUser(user) == -1L) {
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @GetMapping("/users/login")
    public String loginForm() {
        return "/login";
    }

    @PostMapping("/users/login")
    public ResponseEntity validUser(@RequestBody User user) {
        Long userType = userService.loginUser(user);

        if (userType == null) {
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(userType, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/users/findall")
    public List<User> list(Model model) {
        List<User> users = userService.findAllUser();
        model.addAttribute("users", users);
        return users;
    }



}
