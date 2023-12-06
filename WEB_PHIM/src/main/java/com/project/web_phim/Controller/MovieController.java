package com.project.web_phim.Controller;
import com.project.web_phim.Model.*;
import com.project.web_phim.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/movies")
public class MovieController{

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/images/movie";
    @Autowired
    private MovieService movieService;

    @Autowired
    private GenreService genreService;


    @Autowired
    private MovieActorService movieActorService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private MovieGenreService movieGenreService;


    @GetMapping("")
    public String index(Model model,@RequestParam(name = "page",defaultValue = "1") Integer page){
        Page<Movie> movieList=movieService.pagiantioṇ(page);
        model.addAttribute("Movie",movieList);
        model.addAttribute("totalPage",movieList.getTotalPages());
        model.addAttribute("currentPage",page);
        return "movie";
    }

    @GetMapping("/add")
    public String addmovie(Model model){
        List<Genre>genreList= (List<Genre>) genreService.getAllGenres();
        List<Actor>actorList=(List<Actor>)actorService.getAllActors();
        model.addAttribute("Genre",genreList);
        model.addAttribute("Actor",actorList);
        return "addmovie";
    }

    @PostMapping("/add")
    public String processadd(@RequestParam("image") MultipartFile image, @RequestParam("movie_name") String name,
                             @RequestParam("nation") String nation, @RequestParam("status") String status,@RequestParam("genres[]") List<String> genre,
                             @RequestParam("actors[]") List<String> actor, @RequestParam("year") String year,@RequestParam("duration") String duration,
                             @RequestParam("description") String description, RedirectAttributes redirectAttributes) throws IOException {
        try {
            StringBuilder fileNames = new StringBuilder();
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, image.getOriginalFilename());
            fileNames.append(image.getOriginalFilename());
            Files.write(fileNameAndPath, image.getBytes());
        } catch (IOException e) {
            // Handle the IOException here
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to upload file: " + e.getMessage());
            return "redirect:/add";
        }
        Movie movie=new Movie();
        movie.setName(name);
        movie.setImgSrc(image.getOriginalFilename());
        movie.setDuration(duration);
        movie.setDescription(description);
        movie.setRating(5.0);
        movie.setStatus(status);
        movie.setNumLikes(0);
        movie.setNumViews(0);
        movie.setNation(nation);
        movie.setReleasedYear(year);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = formatter.format(now);
        movie.setCreatedDate(formattedDate);
        movie.setUpdatedDate(formattedDate);
        try {
            movieService.saveMovie(movie);
            //Actor
            List<MovieActor> _movieActor=new ArrayList<>();
            for(String s:actor){
                Actor _actor=actorService.findactorbyname(s);
                MovieActor movieActor=new MovieActor();
                movieActor.setMovie(movie);
                movieActor.setActor(_actor);
                movieActorService.saveMovieActor(movieActor);
                _movieActor.add(movieActor);
            }

            movie.setActorlist(_movieActor);

            for (String s:actor){
                Actor _actor=actorService.findactorbyname(s);
                _actor.setActorList(_movieActor);
                actorService.saveActor(_actor);
            }

            //Genre
            List<MovieGenre> _movieGenre=new ArrayList<>();
            for(String s:genre){
                Genre _genre=genreService.findbyname(s);
                MovieGenre movieGenre=new MovieGenre();
                movieGenre.setMovie(movie);
                movieGenre.setGenre(_genre);
                movieGenreService.saveMovieGenre(movieGenre);
                _movieGenre.add(movieGenre);
            }

            for (String s:genre){
                Genre _genre=genreService.findbyname(s);
                _genre.setGenreList(_movieGenre);
                genreService.saveGenre(_genre);
            }


            movie.setGenrelist(_movieGenre);
            movieService.saveMovie(movie);

            redirectAttributes.addFlashAttribute("message", "Add movie " + name + " successfully");
            redirectAttributes.addFlashAttribute("type","success");
            return "redirect:/movies";
        }
        catch (Exception e){
            redirectAttributes.addFlashAttribute("type","danger");
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/movies";
        }
    }


    @GetMapping("/update/{id}")
    public String updatemovie(@PathVariable Long id,Model model){
       Optional<Movie> movie =movieService.getMovie(id);
       Movie _movie=new Movie();
       if(movie.isPresent()){
            _movie=movie.get();
       }
       List<Genre> genreList= (List<Genre>) genreService.getAllGenres();
       List<Actor> actorList= (List<Actor>) actorService.getAllActors();
       model.addAttribute("Movie",_movie);
       model.addAttribute("Genre",genreList);
       model.addAttribute("Actor",actorList);
        return "updatemovie";
    }

    @PostMapping("/update/{id}")
    public String processupdate(@PathVariable Long id,@RequestParam("name") String name,
                                @RequestParam("nation") String nation,@RequestParam("status") String status,
                                @RequestParam("genres[]") List<String> genre, @RequestParam("image") MultipartFile image,@RequestParam("actors[]") List<String>actor,
                                @RequestParam("year") String year ,@RequestParam("description") String descripton,
                                @RequestParam("view") Integer view,@RequestParam("like") Integer like,
                                @RequestParam("rating") Double rating,@RequestParam("duration") String duration,
                                @RequestParam("old_image") String oldimage,RedirectAttributes redirectAttributes
    ) throws IOException{

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = formatter.format(now);
        String newimage="";
        StringBuilder fileNames = new StringBuilder();
        Path oldpath=Paths.get(UPLOAD_DIRECTORY,oldimage);
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, image.getOriginalFilename());
        if(!image.getOriginalFilename().isEmpty()){
            Files.delete(oldpath);
            newimage=image.getOriginalFilename();
            fileNames.append(newimage);
            Files.write(fileNameAndPath, image.getBytes());
            Movie _movie=movieService.getMovie(id).get();
            Path old = Paths.get(UPLOAD_DIRECTORY,_movie.getImgSrc());
            File file = new File(String.valueOf(old));
            file.delete();
        }
        else{
            newimage=oldimage;
        }
        //Movie Present
        Movie movie=movieService.getMovie(id).get();
        movie.setGenrelist(new ArrayList<>());
        movie.setActorlist(new ArrayList<>());

        //Movie new
        movie.setStatus(status);
        movie.setRating(rating);
        movie.setDuration(duration);
        movie.setName(name);
        movie.setImgSrc(newimage);
        movie.setNumLikes(like);
        movie.setReleasedYear(year);
        movie.setNumViews(view);
        movie.setUpdatedDate(formattedDate);
        movie.setNation(nation);
        movie.setDescription(descripton);
        movieService.saveMovie(movie);

        List<MovieActor>movieActors= (List<MovieActor>) movieActorService.getAllMovieActors();
        List<MovieGenre>movieGenres= (List<MovieGenre>) movieGenreService.getAllMovieGenres();
        List<Actor>actorList= (List<Actor>) actorService.getAllActors();
        List<Genre>genreList= (List<Genre>) genreService.getAllGenres();
        for(MovieGenre movieGenre : movieGenres) {
            if(movieGenre.getMovie().getId().equals(id)){
                movieGenreService.deleteMovieGenre(movieGenre.getId());
            }
            for(Genre _genre:genreList){
                if(_genre.getID().equals(movieGenre.getGenre().getID())){
                    _genre.setGenreList(new ArrayList<>());
                }
            }
        }
        for(MovieActor movieActor : movieActors) {
            if(movieActor.getMovie().getId().equals(id)){
                movieActorService.deleteMovieActor(movieActor.getId());
            }
            for(Actor _actor:actorList){
                if(_actor.getID().equals(movieActor.getActor().getID())){
                    _actor.setActorList(new ArrayList<>());
                }
            }
        }
        List<MovieActor>movieActors1=new ArrayList<>();
        for(String _actor:actor){
            Actor newactor=actorService.findactorbyname(_actor);
            MovieActor movieActor=new MovieActor();
            movieActor.setActor(newactor);
            movieActor.setMovie(movie);
            movieActorService.saveMovieActor(movieActor);
            movieActors1.add(movieActor);
        }
        for(String _actor:actor){
            Actor newactor=actorService.findactorbyname(_actor);
            newactor.setActorList(movieActors1);
            actorService.saveActor(newactor);
        }

        List<MovieGenre>movieGenres1=new ArrayList<>();
        for(String _genre:genre){
            Genre genre1=genreService.findbyname(_genre);
            MovieGenre movieGenre=new MovieGenre();
            movieGenre.setGenre(genre1);
            movieGenre.setMovie(movie);
            movieGenreService.saveMovieGenre(movieGenre);
            movieGenres1.add(movieGenre);
        }
        for(String _genre:genre){
            Genre newgenre=genreService.findbyname(_genre);
            newgenre.setGenreList(movieGenres1);
            genreService.saveGenre(newgenre)    ;
        }
        movie.setGenrelist(movieGenres1);
        movie.setActorlist(movieActors1);
        try {
            movieService.saveMovie(movie);
            redirectAttributes.addFlashAttribute("message", "Update movie " + name + " successfully");
            redirectAttributes.addFlashAttribute("type","success");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("type","danger");
        }
        return "redirect:/movies";
    }

    @GetMapping("/detail/{id}")
    public String detailmovie(Model model,@PathVariable Long id) {
        Optional<Movie> movie=movieService.getMovie(id);
        model.addAttribute("movie",movie.get());
        return "detailmovie";
    }

    @PostMapping("/delete/{id}")
    public String deletemovie(@PathVariable Long id,RedirectAttributes redirectAttributes ){
        Optional<Movie> movie=movieService.getMovie(id);
        if(movie.isEmpty()){
            return null;
        }
        try {
            Movie _movie=movie.get();
            List<MovieActor>movieActors= (List<MovieActor>) movieActorService.getAllMovieActors();
            List<MovieGenre>movieGenres= (List<MovieGenre>) movieGenreService.getAllMovieGenres();
            for(MovieActor movieActor : movieActors){
                if(Objects.equals(movieActor.getMovie().getId(), id)){
                    movieActorService.deleteMovieActor(movieActor.getId());
                }
            }

            for(MovieGenre movieGenre : movieGenres){
                if(movieGenre.getMovie().getId().equals(id)){
                    movieGenreService.deleteMovieGenre(movieGenre.getId());
                }
            }
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY,_movie.getImgSrc());
            File file = new File(String.valueOf(fileNameAndPath));
            file.delete();
            movieService.deleteMovie(id);
            redirectAttributes.addFlashAttribute("message", "Delete movie " + movie.get().getName() + " successfully");
            redirectAttributes.addFlashAttribute("type","success");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("type","danger");
        }

        return "redirect:/movies";
    }
}
