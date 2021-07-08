package com.example.springusercrud.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/users")
    public String showUserList(Model model){
        List<User> listUser = userService.listAll();
         model.addAttribute("listUser",listUser);

        return "users";
    }

    @GetMapping("/users/new")
    public String showNewForm(Model model){
        model.addAttribute("user",new User());
        model.addAttribute("pageTitle","Add New User");
        return "users-form";
    }
    @PostMapping("users/save")
    public String saveUser(User user, RedirectAttributes ra){
        userService.save(user);
        ra.addFlashAttribute("message","The user has been saved successfully !");
        return "redirect:/users";
    }
    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id,Model model, RedirectAttributes ra) {

        try {
           User user = userService.get(id);
            model.addAttribute("user",user);
            model.addAttribute("pageTitle","Edit User " + id + "");
            return "users-form";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message",e.getMessage());
            return "redirect:/users";
        }
    }
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            userService.delete(id);

        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());

        }
        return "redirect:/users";
    }
}
