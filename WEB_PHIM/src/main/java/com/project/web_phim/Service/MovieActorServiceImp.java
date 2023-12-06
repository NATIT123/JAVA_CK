package com.project.web_phim.Service;

import com.project.web_phim.Model.Genre;
import com.project.web_phim.Model.MovieActor;
import com.project.web_phim.Repository.MovieActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieActorServiceImp implements MovieActorService{

    @Autowired
    private MovieActorRepository movieActorReposioty;
    @Override
    public Iterable<MovieActor> getAllMovieActors() {
        return movieActorReposioty.findAll();
    }

    @Override
    public Optional<MovieActor> getMovieActor(Long id) {
        return movieActorReposioty.findById(id);
    }

    @Override
    public MovieActor updateMovieActor(Long id, MovieActor acc) {
        return null;
    }

    @Override
    public void deleteMovieActor(Long id) {
        movieActorReposioty.deleteById(id);
    }

    @Override
    public void saveMovieActor(MovieActor movieActor) {
        movieActorReposioty.save(movieActor);
    }

    @Override
    public void deleteAllMovieActors() {

    }

    @Override
    public Iterable<MovieActor> getCustomizedMovieActorList() {
        return null;
    }
}
