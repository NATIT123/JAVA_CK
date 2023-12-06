package com.project.web_phim.Controller;

import com.project.web_phim.Model.*;
import com.project.web_phim.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {
    @Autowired
    private MovieService movieService;


    @Autowired
    private MovieActorService movieActorService;

    @Autowired

    private MovieGenreService movieGenreService;
    @GetMapping("/movies/search")
    public ResponseEntity<List<Movie>> search(@RequestParam(value = "name" ,defaultValue = "1") String name) {
        if(name.equals("1")||name.isEmpty()){
            return new ResponseEntity<>((List<Movie>)movieService.getAllMovies(),HttpStatus.OK);
        }
        return new ResponseEntity<>(movieService.searchall(name), HttpStatus.OK);
    }

    @GetMapping("/movies/search/actor")
    public ResponseEntity<Actor>searchactor(@RequestParam(value="id") Long id){
        MovieActor movieActor=movieActorService.getMovieActor(id).get();
        return new ResponseEntity<>(movieActor.getActor(),HttpStatus.OK);
    }

    @GetMapping("/movies/search/genre")
    public ResponseEntity<Genre>searchgenre(@RequestParam(value="id") Long id){
        MovieGenre movieGenre=movieGenreService.getMovieGenre(id).get();
        return new ResponseEntity<>(movieGenre.getGenre(),HttpStatus.OK);
    }


}
