package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class UserController {

    private UserServiceImpl userService;
    private RoleRepository roleRepository;

    @Autowired
    public UserController(UserServiceImpl userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }
    @GetMapping("/admin")
    public String all (Model model) {
        List<User> allUs = userService.getAll();
        model.addAttribute("users",  allUs);
        return "index-all";
    }

    @GetMapping("/user")
    public String all (Principal principal, Model model) {
        User user = userService.findByName(principal.getName());
        model.addAttribute("users", user);
        return "user";
    }

    @GetMapping("/new")
    public String addUser (Model model) {
        User user = new User();
        model.addAttribute("userAdd",  user);
        return "redirect:/admin";
    }

    @PostMapping("/admin")
    public String saveUser(@ModelAttribute("userAdd") User user, Principal principal) {
        List<Long> roleIds = user.getRoleIds();
            List<Integer> roleIntIds = roleIds.stream()
                    .map(Long::intValue)
                    .collect(Collectors.toList());
            List<Role> roles = new ArrayList<>(roleRepository.findAllById(roleIntIds));
            if (user.getRoles() == null) {
                user.setRoles(new ArrayList<>()); // Создаем новый список, если getRoles() возвращает null
            }
            userService.saveUserWithRoles(user, new ArrayList<>(roles));
            return "redirect:/admin";
    }

    @GetMapping("/admin/edit")
    public String updateUser(@RequestParam("userId") int id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("userAdd", user);
        return "index-info";
    }

    @GetMapping("/admin/delete-get")
    public String deleteGetUser(@RequestParam("userId") int id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("userDel", user);
        return "index-info";
    }

    @GetMapping("/admin/delete")
    public String deleteUser(@RequestParam("userId") int id, Model model) {
        User user = userService.deleteUser(id);
        return "redirect:/admin";
    }


}
