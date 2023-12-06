package com.project.web_phim.Controller;
import com.project.web_phim.Model.Account;
import com.project.web_phim.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController{
    @GetMapping("")
    public String index(Model model){
        List<Account> accountList= (List<Account>) accountService.getAllAccounts();
        model.addAttribute("Account",accountList);
        return "user";
    }

    @GetMapping("/detail/{id}")
    public String getprofile(@PathVariable Long id,Model model){
        Account account=accountService.getAccount(id).get();
        model.addAttribute("Account",account);
        return "profile";
    }
    @GetMapping("/add")
    public String adduser(){
        return "adduser";
    }

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/images/avatar";

    @Autowired
    private AccountService accountService;
    @PostMapping("/add")
    public String processad(@RequestParam("name") String name, @RequestParam("avatar")MultipartFile avatar,
                            @RequestParam("email") String email, @RequestParam("role") String role, RedirectAttributes redirectAttributes) {
        try {
            StringBuilder fileNames = new StringBuilder();
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, avatar.getOriginalFilename());
            fileNames.append(avatar.getOriginalFilename());
            Files.write(fileNameAndPath, avatar.getBytes());
        } catch (IOException e) {
            // Handle the IOException here
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to upload file: " + e.getMessage());
            return "redirect:/users/add";
        }
        try {

            Account account = new Account();
            account.setEmail(email);
            account.setRole(role);
            account.setFullname(name);
            account.setUsername(email.substring(0, email.indexOf('@')));
            account.setPassword(email.substring(0, email.indexOf('@')));
            account.setImage(avatar.getOriginalFilename());
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = formatter.format(now);
            account.setCreatedAt(formattedDate);
            account.setUpdatedAt(formattedDate);
            accountService.saveAccount(account);
            redirectAttributes.addFlashAttribute("message", "Add account " + name + " successfully");
            redirectAttributes.addFlashAttribute("type", "success");
            return "redirect:/users";
        }
        catch (Exception e){
            redirectAttributes.addFlashAttribute("type","danger");
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            System.out.println(e.getMessage());
            return "redirect:/users";
        }

    };

    @GetMapping("/update/{id}")
    public String updateuser(@PathVariable("id") Long id,Model model){
        Account account=accountService.getAccount(id).get();
        model.addAttribute("Account",account);
        return "updateuser";
    }

    @PostMapping("/update/{id}")
    public String processupdate(@PathVariable(value="id") Long id,@RequestParam("name") String name,
                                @RequestParam("email") String email,@RequestParam("username") String username,RedirectAttributes redirectAttributes,
                                @RequestParam("role") String role,@RequestParam("avatar") MultipartFile avatar,@RequestParam("old_image") String oldimage ) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = formatter.format(now);
        String newavatar="";
        StringBuilder fileNames = new StringBuilder();
        Path oldpath=Paths.get(UPLOAD_DIRECTORY,oldimage);
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, avatar.getOriginalFilename());
        if(!avatar.getOriginalFilename().isEmpty()){
            Files.delete(oldpath);
            newavatar=avatar.getOriginalFilename();
            fileNames.append(newavatar);
            Files.write(fileNameAndPath, avatar.getBytes());
            Account _account=accountService.getAccount(id).get();
            Path old = Paths.get(UPLOAD_DIRECTORY,_account.getImage());
            File file = new File(String.valueOf(old));
            file.delete();
        }
        else{
            newavatar=oldimage;
        }
        Account _account=accountService.getAccount(id).get();
        _account.setUpdatedAt(formattedDate);
        _account.setRole(role);
        _account.setEmail(email);
        _account.setImage(newavatar);
        _account.setFullname(name);
        accountService.saveAccount(_account);
        try {
            accountService.saveAccount(_account);
            redirectAttributes.addFlashAttribute("message", "Update user " + name + " successfully");
            redirectAttributes.addFlashAttribute("type","success");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("type","danger");
        }
        return "redirect:/users";
    }
    @PostMapping("/delete/{id}")
    public String deleteaccount(@PathVariable Long id,RedirectAttributes redirectAttributes ){
        Optional<Account> account=accountService.getAccount(id);
        if(account.isEmpty()){
            return null;
        }
        try {
            Account _account=account.get();
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY,_account.getImage());
            File file = new File(String.valueOf(fileNameAndPath));
            file.delete();
            accountService.deleteAccount(id);
            redirectAttributes.addFlashAttribute("message", "Delete user " +account.get().getFullname() + " successfully");
            redirectAttributes.addFlashAttribute("type","success");
        }
        catch (Exception e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("type","danger");
        }

        return "redirect:/users";
    }






}
