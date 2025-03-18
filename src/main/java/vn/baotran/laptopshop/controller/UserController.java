package vn.baotran.laptopshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.baotran.laptopshop.domain.User;
import vn.baotran.laptopshop.service.UserService;

import java.util.List;

@Controller
public class UserController {
    // DI: Dependency injection
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        List<User> userList = this.userService.getAllUserByEmail("temqt2003@gmail.com");
        System.out.println(userList);
        model.addAttribute("baotran", "from controller with model");
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/user/table-user";
    }

    @GetMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable Long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        return "admin/user/show";
    }

    @GetMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping(value = "/admin/user/create")
    public String createUserPage(@ModelAttribute User newUser) {
        System.out.println("Run here !" + newUser);
        this.userService.handleSaveUser(newUser);
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable Long id) {
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("newUser", currentUser);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String postUpdateUser(@ModelAttribute("newUser") User user) {
        User currentUser = this.userService.getUserById(user.getId());
        if (currentUser != null) {
            currentUser.setAddress(user.getAddress());
            currentUser.setFullName(user.getFullName());
            currentUser.setPhone(user.getPhone());

            this.userService.handleSaveUser(currentUser);
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        model.addAttribute("newUser", new User());
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUser(@ModelAttribute("newUser") User user) {
        this.userService.deleteAUser(user.getId());
        return "redirect:/admin/user";
    }

}