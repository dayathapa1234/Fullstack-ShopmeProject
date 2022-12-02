package com.shopme.admin.user;

import com.shopme.Role;
import com.shopme.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private  UserService userService;

    @GetMapping("/users")
    public  String listAll(Model model){
        List<User> userList = userService.listAll();
        model.addAttribute("userList",userList);
        return "users";
    }

    @GetMapping("/users/new")
    public String newUser(Model model){
        List<Role> listRoles = userService.listRoles();
        User user = new User();
        user.setEnabled(true);
        model.addAttribute("user",user);
        model.addAttribute("listRoles",listRoles);
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user){
        System.out.println(user);
        return "redirect:/users";
    }
}
