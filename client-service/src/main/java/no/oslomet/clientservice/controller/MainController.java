package no.oslomet.clientservice.controller;

import no.oslomet.clientservice.model.Tweet;
import no.oslomet.clientservice.model.User;
import no.oslomet.clientservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private TweetService tweetService;
    @Autowired
    private HashtagService hashtagService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private User loggedInUser;
    private String imageFolder = "C:\\Users\\mebix\\Documents\\Github\\dave3615_finalproject\\client-service\\src\\main\\resources\\static\\images\\";

    @GetMapping("/")
    public String home(Model model){
        /*return "login";*/
        model.addAttribute("user", loggedInUser);
        model.addAttribute("allTweets", tweetService.getAllTweets());
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/home")
    public String homePage(Model model){
        System.out.println("homePage");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUserName(auth.getName());
        loggedInUser = user;
        if(user != null) model.addAttribute("user", loggedInUser);
        System.out.println("homePage2: " + loggedInUser.getUsername() + ", " + loggedInUser.getRoleId().getRoleName() + ", " + loggedInUser.getPassword() + ", " + loggedInUser.getProfileImage());
        model.addAttribute("allTweets", tweetService.getAllTweets());
        model.addAttribute("user", loggedInUser);
        return "index";
    }

    @GetMapping("/loguserout")
    public String logout(){
        System.out.println("logout");
        loggedInUser = null;
        return "logout";
    }

    @GetMapping("/signup")
    public String signup(){
        System.out.println("signup");
        return "signup";
    }

    @GetMapping("/signupAdmin")
    public String signupAdmin(){
        System.out.println("signupAdmin");
        return "signupAdmin";
    }

    @PostMapping("/processRegistration")
    public String register(@ModelAttribute("user") User user){
        System.out.println("processRegistration");
        user.setRoleId(roleService.getRoleById(1));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/";
    }

    @PostMapping("/processRegistrationAdmin")
    public String registerAdmin(@ModelAttribute("user") User adminUser){
        System.out.println("processRegistrationAdmin");
        adminUser.setRoleId(roleService.getRoleById(2));
        userService.saveUser(adminUser);
        return "redirect:/";
    }

    @PostMapping("/saveTweet")
    public String saveTweet(@ModelAttribute("tweet")Tweet tweet, @RequestParam("files") MultipartFile[] file, RedirectAttributes redirectAttributes){

        if (file.length > 3) {
            redirectAttributes.addFlashAttribute("message", "You can only upload 4 photos per tweet");
            return "redirect:/home";
        }else{
            uploadImage(file,tweet);
            tweet.setDate(new Date());
            tweet.setUserId(loggedInUser.getId());
            tweetService.saveTweet(tweet);
        }
        return "redirect:/userprofile";
    }

    public void uploadImage(MultipartFile[] file, Tweet tweet) {
        System.out.println("uploadImage " + file.length);
        Arrays.asList(file).forEach(afile -> {
            byte[] bytes = new byte[0];
            try {
                bytes = afile.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Path path = Paths.get(imageFolder + afile.getOriginalFilename());
            System.out.println("afile.getOriginalFilename(): " + afile.getOriginalFilename().toUpperCase());
            try {
                if(!afile.isEmpty()){
                    Files.write(path, bytes);
                    tweet.getImagePathList().add("/images/"+afile.getOriginalFilename());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @GetMapping("/userprofile")
    public String profilePage(Model model){
        model.addAttribute("user", loggedInUser);
        model.addAttribute("allTweets", tweetService.getTweetsByUserId(loggedInUser.getId()));
        return "userprofile";
    }

    @GetMapping("/editprofile")
    public String editProfile(Model model){
        model.addAttribute("user", loggedInUser);
        return "edituserprofile";
    }

    @GetMapping("/editprofile/{id}")
    public String editOtherUserProfile(@PathVariable long id, Model model){
        model.addAttribute("user", userService.getUserById(id));
        return "edituserprofile";
    }

    @GetMapping("/deleteaccount/{id}")
    public String deleteAccount(@PathVariable long id){
        System.out.println("deleteAccount");
        tweetService.deleteTweetByUserId(id);
        userService.deleteUser(id);
        loggedInUser = null;
        return "redirect:/";
    }

    @PostMapping("/updateuser")
    public String updateUser(@ModelAttribute("user") User editedUser, @RequestParam("file") MultipartFile file){
        System.out.println("updateUser");
        editedUser.setRoleId(loggedInUser.getRoleId());
        if(editedUser.getPassword().compareTo(loggedInUser.getPassword()) != 0) editedUser.setPassword(passwordEncoder.encode(editedUser.getPassword()));

        if(file.isEmpty()){
            editedUser.setProfileImage(loggedInUser.getProfileImage());
        }else{
            uploadSingleImage(file);
            editedUser.setProfileImage("/images/profileImage/" + file.getOriginalFilename());
        }
        userService.saveUser(editedUser);
        return "redirect:/home";
    }

    public void uploadSingleImage(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(imageFolder + "/profileImage/" + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/adminpage")
    public String adminPage(Model model){
        model.addAttribute("user", loggedInUser);
        model.addAttribute("allUsers", userService.getAllUsers());
        return "adminpage";
    }

}
