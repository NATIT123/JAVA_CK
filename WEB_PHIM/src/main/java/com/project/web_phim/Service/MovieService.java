package com.project.web_phim.Service;

import com.project.web_phim.Model.Movie;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
public interface MovieService {
    public Iterable<Movie> getAllMovies();

    public Optional<Movie> getMovie(Long id);

    public Movie updateMovie(Long id,Movie movie);

    public void deleteMovie(Long id);

    public void saveMovie(Movie movie);

    public void deleteAllMovies();

    public Iterable<Movie> getCustomizedMovieList();

    public List<Movie> searchall(String name);

    public Page<Movie>pagiantioṇ(Integer page);

    public List<Movie> findcategory (String category);

    public List<Movie> trendmovie();

    public List<Movie>recentmovie();
}
