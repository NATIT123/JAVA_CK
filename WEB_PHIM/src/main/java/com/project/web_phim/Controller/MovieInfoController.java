package com.project.web_phim.Controller;

import com.project.web_phim.Model.Movie;
import com.project.web_phim.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class MovieInfoController {
    @Autowired
    private MovieService movieService;
    @GetMapping("/info/{id}")
    public String index(Model model, @PathVariable Long id) {
        Optional<Movie> movie = movieService.getMovie(id);
        model.addAttribute("movie",movie.get());
        return "info";
    }
}
