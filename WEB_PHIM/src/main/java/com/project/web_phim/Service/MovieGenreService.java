package com.project.web_phim.Service;

import com.project.web_phim.Model.MovieGenre;

import java.util.Optional;

public interface MovieGenreService {
    public Iterable<MovieGenre> getAllMovieGenres();

    public Optional<MovieGenre> getMovieGenre(Long id);

    public MovieGenre updateMovieGenre(Long id,MovieGenre acc);

    public void deleteMovieGenre(Long id);

    public void saveMovieGenre(MovieGenre acc);

    public void deleteAllMovieGenres();

    public Iterable<MovieGenre> getCustomizedMovieGenreList();
}
