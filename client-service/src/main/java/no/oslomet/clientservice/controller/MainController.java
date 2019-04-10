package no.oslomet.clientservice.controller;

import no.oslomet.clientservice.model.*;
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
import java.text.ParseException;
import java.time.LocalDateTime;
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
    private FollowingService followingService;
    @Autowired
    private FollowerService followerService;
    @Autowired
    private RetweetService retweetService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private User loggedInUser;
    private List<Tweet> allTweets = new ArrayList<>();
    private String imageFolder = "C:\\Users\\mebix\\Documents\\Github\\dave3615_finalproject\\client-service\\src\\main\\resources\\static\\images\\";

    @GetMapping("/")
    public String home(Model model){
        /*return "login";*/
        indexModelAttribute(model, sortTweetByDateTime(tweetService.getAllTweets()));
        /*System.out.println("home" + LocalDateTime.now().getDayOfMonth() + ", " + LocalDateTime.now().getDayOfWeek() + ", " + LocalDateTime.now().getDayOfYear()
        + ", " + LocalDateTime.now().getMonth() + ", " + LocalDateTime.now().getMonthValue() + ", " + LocalDateTime.now().getYear() + ", ");*/
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
        indexModelAttribute(model, sortTweetByDateTime(tweetService.getAllTweets()));
        /*if(user != null) model.addAttribute("user", loggedInUser);*/
        System.out.println("homePage: " + loggedInUser.getUsername() + ", " + loggedInUser.getRoleId().getRoleName() + ", " + loggedInUser.getPassword() + ", " + loggedInUser.getProfileImage());
        /*int t = LocalDateTime.now().getHour() - tweetService.getTweetById(1).getDateTime().getHour();*/

        return "index";
    }

    public List<Tweet> sortTweetByDateTime (List<Tweet> tweetList){
        tweetList.sort(Comparator.comparing(Tweet::getDateTime));
        Collections.reverse(tweetList);
        return tweetList;
    }

    @GetMapping("/loguserout")
    public String logout(){
        System.out.println("logout");
        loggedInUser = null;
        return "redirect:/";
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
        adminUser.setPassword(passwordEncoder.encode(adminUser.getPassword()));
        userService.saveUser(adminUser);
        return "redirect:/";
    }

    @PostMapping("/saveTweet")
    public String saveTweet(@ModelAttribute("tweet")Tweet tweet, @RequestParam("files") MultipartFile[] file, RedirectAttributes redirectAttributes) throws ParseException {

        if (file.length > 3) {
            redirectAttributes.addFlashAttribute("message", "You can only upload 4 photos per tweet");
            return "redirect:/home";
        }else{

            uploadImage(file,tweet);
            tweet.setDateTime(LocalDateTime.now());
            System.out.println("getDateTime: " + tweet.getDateTime());
            tweet.setUserId(loggedInUser.getId());
            tweetService.saveTweet(tweet);
        }
        return "redirect:/";
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

    @GetMapping("/userprofile/{id}")
    public String profilePage(@PathVariable long id, Model model){
        userprofileModelAttribute(model, new ArrayList<>(), id);
        return "userprofile";
    }

    public boolean checkIfFollowing(long id){
        for (Following x : followingService.getFollowingByOwnerId(loggedInUser.getId())) {
            if (x.getUser().getId() == id) return true;
        }
        return false;
    }

    @GetMapping("/editprofile/{id}")
    public String editProfile(@PathVariable long id, Model model){
        model.addAttribute("user", userService.getUserById(id));
        return "edituserprofile";
    }

    @GetMapping("/deleteaccount/{id}")
    public String deleteAccount(@PathVariable long id, RedirectAttributes redirectAttributes){
        System.out.println("deleteAccount");
        String userToDelete = userService.getUserById(id).getUsername();
        tweetService.deleteTweetByUserId(id);
        userService.deleteUser(id);
        loggedInUser = loggedInUser.getId() == id ? (null) : (loggedInUser);
        redirectAttributes.addFlashAttribute("deletemessage", loggedInUser != null ?
                (userToDelete + " profile has been deleted.") : ("Your profile has been deleted."));
        return loggedInUser != null ? "redirect:/adminpage" :  "redirect:/";
    }

    @PostMapping("/updateuser")
    public String updateUser(@ModelAttribute("user") User editedUser, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
        System.out.println("updateUser: ");
        User user = userService.getUserById(editedUser.getId());
        editedUser.setRoleId(loggedInUser.getRoleId());
        if(editedUser.getPassword().compareTo(user.getPassword()) != 0) editedUser.setPassword(passwordEncoder.encode(editedUser.getPassword()));

        if(file.isEmpty()){
            editedUser.setProfileImage(loggedInUser.getProfileImage());
        }else{
            uploadSingleImage(file);
            editedUser.setProfileImage("/images/profileImage/" + file.getOriginalFilename());
        }
        userService.saveUser(editedUser);
        redirectAttributes.addFlashAttribute("message", editedUser.getId() == loggedInUser.getId() ?
                ("Your profile has been saved.") : (editedUser.getFirstName() + "'s profile has been saved."));

        return "redirect:/editprofile";
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

    @GetMapping("/follow/{id}")
    public String followAUser(@PathVariable long id, Model model){
        System.out.println("followAUser");
        updateFollowingList(id);
        updateFollowerList(id);
        userService.saveUser(loggedInUser);
        model.addAttribute("user", userService.getUserById(id));
        return "redirect:/userprofile/{id}";
    }

    public void updateFollowingList(long id){
        loggedInUser.getFollowingList().add(new Following(loggedInUser.getId(), userService.getUserById(id)));
    }

    public void updateFollowerList(long id){
        System.out.println("updateFollowerList: " + userService.getUserById(id).getUsername());
        System.out.println(userService.getUserById(id).getFollowerList().size());
        followerService.saveFollower(new Follower(id, loggedInUser));
        /*userService.getUserById(id).getFollowerList().add(new Follower());*/
    }

    @GetMapping("/unfollow/{id}")
    public String unfollowAUser(@PathVariable long id){
        System.out.println("unfollowAUser");
        followingService.deleteAUserFollowing(loggedInUser.getId(), id);
        followerService.deleteAUserFollower(id, loggedInUser.getId());
        return "redirect:/userprofile/{id}";
    }

    @GetMapping("/allfollowing/{id}")
    public String getAllFollowing(@PathVariable long id, Model model){
        System.out.println("getAllFollowing");
        List<User> followingList = new ArrayList<>();
        followingService.getFollowingByOwnerId(id).forEach(x -> followingList.add(x.getUser()));
        userprofileModelAttribute(model, followingList, id);

        return "userprofile";
    }

    @GetMapping("/allfollower/{id}")
    public String getAllFollower(@PathVariable long id, Model model){
        System.out.println("getAllFollower");
        List<User> followerList = new ArrayList<>();
        followerService.getFollowerByOwnerId(id).forEach(x -> followerList.add(x.getUser()));
        userprofileModelAttribute(model, followerList, id);

        return "userprofile";
    }

    @GetMapping("/tweetsfromfollowing")
    public String tweetsFromFollowing(Model model){
        System.out.println("tweetsFromFollowing");
        List<Tweet> tweetsFromFollowings = new ArrayList<>();
        for (Following f : followingService.getFollowingByOwnerId(loggedInUser.getId()) ){
            System.out.println(f.getUser().getUsername());
            tweetService.getTweetsByUserId(f.getUser().getId()).forEach(x -> tweetsFromFollowings.add(x));
        }

        tweetsFromFollowings.forEach(x -> System.out.println(x.getUserId() + ", " + x.getText()));
        indexModelAttribute(model, tweetsFromFollowings);
        return "index";
    }

    @GetMapping("/retweet/{id}")
    public String retweet(@PathVariable long id, Model model){
        System.out.println("retweet");
        retweetService.saveRetweet(new Retweet(loggedInUser.getId(), tweetService.getTweetById(id)));
        List<User> followerList = new ArrayList<>();

        userprofileModelAttribute(model, followerList, id);

        return "redirect:/";
    }

    public Model indexModelAttribute(Model model, List<Tweet> allTweets){
        model.addAttribute("userlist", userService.getAllUsers());
        model.addAttribute("allTweets", allTweets);
        model.addAttribute("user", loggedInUser);
        model.addAttribute("localdatetime", LocalDateTime.now());

        return model;
    }

    public Model userprofileModelAttribute(Model model, List<User> followingList, long id){
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("disableFollowingBtn", id == loggedInUser.getId() ? true : false);
        model.addAttribute("disableEditBtn", id == loggedInUser.getId() ? true : false);
        model.addAttribute("isFollowing", checkIfFollowing(id));
        model.addAttribute("allTweets", tweetService.getTweetsByUserId(id));
        model.addAttribute("numberOfFollowing", followingService.getFollowingByOwnerId(id).size());
        model.addAttribute("numberOfFollower", followerService.getFollowerByOwnerId(id).size());
        model.addAttribute("allFollowing", followingList);
        model.addAttribute("userlist", userService.getAllUsers());
        model.addAttribute("localdatetime", LocalDateTime.now());
        return model;
    }

}
