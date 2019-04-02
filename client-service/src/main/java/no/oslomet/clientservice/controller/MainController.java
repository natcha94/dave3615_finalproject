package no.oslomet.clientservice.controller;

import no.oslomet.clientservice.model.Role;
import no.oslomet.clientservice.model.User;
import no.oslomet.clientservice.service.RoleService;
import no.oslomet.clientservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.management.relation.RoleInfo;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    private User loggedInUser;

    @GetMapping("/")
    public String home(){
        return "login";
    }

    @GetMapping("/home")
    public String homePage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUserName(auth.getName());
        loggedInUser = user;
        if(user != null) model.addAttribute("user", loggedInUser);

        return "index";
    }

    @GetMapping("/signup")
    public String signup(Model model){
        System.out.println("signup");
        List<Role> roleList = roleService.getAllRoles();
        Role userRole = roleService.getRoleById(1);
        model.addAttribute("roles", userRole);
        model.addAttribute("user", new User());
        return "signup";
    }

    @GetMapping("/signupAdmin")
    public String signupAdmin(Model model){
        System.out.println("signupAdmin");
        model.addAttribute("roles", roleService.getAllRoles());
        return "signupAdmin";
    }

    @PostMapping("/processRegistration")
    public String register(@ModelAttribute("user") User user){
        System.out.println("processRegistration");
        //System.out.println(user.getUserName() + ", " + user.getRoleId().getRoleName());
        /*Role role = roleService.getRoleById(1);*/
        user.setRoleId(roleService.getRoleById(1));
        System.out.println(user.getRoleId().getRoleName());
        userService.saveUser(user);
        return "redirect:/";
    }

    @PostMapping("/processRegistrationAdmin")
    public String registerAdmin(@ModelAttribute("user") User adminUser){
        System.out.println("processRegistrationAdmin");
        Role adminRole = roleService.getRoleById(2);
        adminUser.setRoleId(adminRole);
        userService.saveUser(adminUser);
        return "redirect:/";
    }
}
