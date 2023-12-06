package com.project.web_phim.Controller;
import com.project.web_phim.Model.Account;
import com.project.web_phim.Model.Genre;
import com.project.web_phim.Model.Movie;
import com.project.web_phim.Model.MovieGenre;
import com.project.web_phim.Service.AccountService;
import com.project.web_phim.Service.GenreService;
import com.project.web_phim.Service.MovieGenreService;
import com.project.web_phim.Service.MovieService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    private GenreService genreService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieGenreService movieGenreService;

    @Autowired
    private AccountService accountService;

    @GetMapping( value={ "/index"})
    public String index(Model model, HttpSession session){
        insert(model,session);
        String username= (String) session.getAttribute("username");
        Long id= (Long) session.getAttribute("id");
        String image= (String) session.getAttribute("image");
        String role= (String) session.getAttribute("role");
        model.addAttribute("username",username);
        model.addAttribute("id",id);
        model.addAttribute("image",image);
        model.addAttribute("role",role);
        return "home";
    }
    @GetMapping("/search/nation")
    public String searchbykeyword(@RequestParam("_nation") String nation,Model model,HttpSession session){
        insert(model,session);
        List<Movie>movieList= (List<Movie>) movieService.getAllMovies();
        List<Movie>_nation=new ArrayList<>();
        for (Movie movie:movieList){
            if(movie.getNation().equals(nation)){
                _nation.add(movie);
                break;
            }
        }
        model.addAttribute("Moviekey",_nation);
        model.addAttribute("tag",nation);
        return "searchmovie";
    }

    @GetMapping("/search/genre")
    public String searchgenre(@RequestParam("_genre") String genre,Model model,HttpSession se){
        insert(model,se);
        List<Movie>movieList= (List<Movie>) movieService.getAllMovies();
        List<Movie>_genre=new ArrayList<>();
        for (Movie movie:movieList){
            for(MovieGenre genre1:movie.getGenrelist()){
                if(genre1.getGenre().getName().equals(genre)){
                    _genre.add(movie);
                    break;
                }
            }
        }
        model.addAttribute("Moviekey",_genre);
        model.addAttribute("tag",genre);
        return "searchmovie";
    }

    @GetMapping("/search/type")
    public String searchtype(@RequestParam("_type") String type,Model model,HttpSession session){
        insert(model,session);
        List<Movie>movieList= (List<Movie>) movieService.getAllMovies();
        type=type.toLowerCase();
        List<Movie>_type=new ArrayList<>();
        for (Movie movie:movieList){
            if(movie.getType().toLowerCase().equals(type)){
                _type.add(movie);
                break;
            }
        }
        model.addAttribute("Moviekey",_type);
        model.addAttribute("tag",type.split(" ")[1]);
        return "searchmovie";
    }

    @GetMapping("/search/recent")
    public String recentupdate(Model model,HttpSession session){
        insert(model,session);
        List<Movie>recentmovie=movieService.recentmovie();
        model.addAttribute("Moviekey",recentmovie);
        model.addAttribute("tag","MỚI CẬP NHẬT");
        return "searchmovie";
    }

    public void insert(Model model,HttpSession session){
        List<Genre> genreList= (List<Genre>) genreService.getAllGenres();
        List<Movie> movies= (List<Movie>) movieService.getAllMovies();
        Set<String> nation=new HashSet<>();
        for (Movie movie:movies){
            nation.add(movie.getNation());
        }
        List<Movie>action=new ArrayList<>();
        for (Movie movie:movies){
            for(MovieGenre _genre: movie.getGenrelist()){
                System.out.println(_genre.getGenre().getName());
                if(_genre.getGenre().getName().equals("Hành động")){
                    action.add(movie);
                    break;
                }
            }
        }

        List<Movie>cartoon=new ArrayList<>();
        for (Movie movie:movies){
            for(MovieGenre _genre: movie.getGenrelist()){
                System.out.println(_genre.getGenre().getName());
                if(_genre.getGenre().getName().equals("Hoạt hình")){
                    cartoon.add(movie);
                    break;
                }
            }
        }

        List<Movie>trend=movieService.trendmovie();

        model.addAttribute("_Genre",genreList);
        model.addAttribute("_Nation",nation);
        model.addAttribute("_MovieAction",action);
        model.addAttribute("_MovieCartoon",cartoon);
        model.addAttribute("_TrendMovie",trend);
    }
    @GetMapping("/login")
    public String login(Model model,HttpSession session)
    {   insert(model,session);
        String usernmae= (String) session.getAttribute("username");
        if(usernmae==null){
            return "login";
        }
        if(!usernmae.isEmpty()){
            return "redirect:/index";
        }
        return "login";
    }

    @PostMapping("/login")
    public String processlogin(@RequestParam("username") String username, @RequestParam("password") String passowrd, RedirectAttributes redirectAttributes,HttpSession session){
        if(accountService.chekclogin(username,passowrd)){
            redirectAttributes.addFlashAttribute("message", "Login successfully");
            redirectAttributes.addFlashAttribute("type","success");
            session.setAttribute("username",username);
            session.setAttribute("id",accountService.findbyusername(username).getId());
            session.setAttribute("image",accountService.findbyusername(username).getImage());
            session.setAttribute("role",accountService.findbyusername(username).getRole().toLowerCase());
        }
        else{
            redirectAttributes.addFlashAttribute("message", "UserName or Password not correct");
            redirectAttributes.addFlashAttribute("type","danger");
            return "redirect:/login";
        }
        return "redirect:/index";

    }

    @GetMapping("/register")
    public String register(Model model,HttpSession session)
    {   insert(model,session);
        String usernmae= (String) session.getAttribute("username");
        if (usernmae==null){
            return "register";
        }
        if(!usernmae.isEmpty()){
            System.out.println(usernmae);
            return "redirect:/index";
        }
        return "register";
    }

    @PostMapping("/register")
    public String processregister(@RequestParam("name") String name,
                                  @RequestParam("email") String email,@RequestParam("pwd") String password,
                                  @RequestParam("_pwd") String confirmpass,RedirectAttributes redirectAttributes){
        if(!password.equals(confirmpass)){
            redirectAttributes.addFlashAttribute("message", "Password and confirmpassword must be the same");
            redirectAttributes.addFlashAttribute("type","danger");
            return "redirect:/register";
        }
        if(accountService.finbyemail(email)){
            redirectAttributes.addFlashAttribute("message", "Email duplicated");
            redirectAttributes.addFlashAttribute("type","danger");
            return "redirect:/register";
        }

        Account account=new Account();
        account.setFullname(name);
        account.setEmail(email);
        account.setUsername(email.substring(0,email.indexOf('@')));
        account.setPassword(email.substring(0,email.indexOf('@')));
        account.setRole("User");
        account.setImage("");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = formatter.format(now);
        account.setCreatedAt(formattedDate);
        account.setUpdatedAt(formattedDate);
        accountService.saveAccount(account);
        redirectAttributes.addFlashAttribute("message", "Register Successfully");
        redirectAttributes.addFlashAttribute("type","success");
        return "redirect:/login";
    }


    @GetMapping("/logout")
    public String logout(Model model,HttpSession session){
        session.setAttribute("username","");
        session.setAttribute("id",0L);
        session.setAttribute("image","");
        session.setAttribute("role","");
        return "redirect:/login";
    }





}
