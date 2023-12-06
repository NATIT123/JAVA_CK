package com.project.web_phim.Service;

import com.project.web_phim.Model.Genre;
import com.project.web_phim.Model.MovieActor;

import java.util.Optional;

public interface MovieActorService {
    public Iterable<MovieActor> getAllMovieActors();

    public Optional<MovieActor> getMovieActor(Long id);

    public MovieActor updateMovieActor(Long id,MovieActor acc);

    public void deleteMovieActor(Long id);

    public void saveMovieActor(MovieActor MovieActor);

    public void deleteAllMovieActors();

    public Iterable<MovieActor> getCustomizedMovieActorList();
}
