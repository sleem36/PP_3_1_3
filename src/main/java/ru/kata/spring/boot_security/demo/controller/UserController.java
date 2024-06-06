package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.List;


@Controller
public class UserController {

    private UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String all (Model model) {
        model.addAttribute("test", "All users");
        List<User> allUs = userService.getAll();
        model.addAttribute("users",  allUs);
        return "index-all";
    }

    @GetMapping("/user")
    public String all (Principal principal, Model model) {
        User user = userService.findByName(principal.getName());
        model.addAttribute("users", user);
        return "index-all";
    }

    @GetMapping("/new")
    public String addUser (Model model) {
        User user = new User();
        model.addAttribute("userAdd",  user);
        return "index-info";
    }

    @PostMapping("/admin")
    public String saveUser (@ModelAttribute("userAdd") User user) {
      //  userService.;
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit")
    public String updateUser(@RequestParam("userId") int id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("userAdd", user);
        return "index-info";
    }

    @GetMapping("/admin/delete")
    public String deleteUser(@RequestParam("userId") int id, Model model) {
        User user = userService.deleteUser(id);
        return "redirect:/admin";
    }
}
