package com.project.web_phim.Service;

import com.project.web_phim.Model.Movie;
import com.project.web_phim.Model.MovieGenre;
import com.project.web_phim.Repository.GenreRepository;
import com.project.web_phim.Repository.MovieGenreRepository;
import com.project.web_phim.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImp implements MovieService{
    @Autowired
    private MovieRepository movieRepository;

    @Autowired

    private MovieGenreRepository movieGenreRepository;

    @Autowired
    private GenreRepository genreRepository;
    @Override
    public Iterable<Movie> getAllMovies() {
        return movieRepository.findAll(Sort.by("name").ascending());
    }

    @Override
    public Optional<Movie> getMovie(Long id) {
        return movieRepository.findById(id);
    }


    @Override
    public Movie updateMovie(Long id, Movie movie) {
        Movie _movie = movieRepository.findById(id).orElse(null);
        if(_movie == null){
            return null;
        }
        _movie.setName((movie.getName()));
        _movie.setStatus(movie.getStatus());
        _movie.setReleasedYear(movie.getReleasedYear());
        _movie.setDuration(movie.getDuration());
        _movie.setDescription(movie.getDescription());
        _movie.setNumLikes(movie.getNumLikes());
        _movie.setNumViews(movie.getNumViews());
        _movie.setRating(movie.getRating());
        _movie.setUpdatedDate(movie.getUpdatedDate());
        _movie.setImgSrc(movie.getImgSrc());
        return movieRepository.save(_movie);
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    public void saveMovie(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public void deleteAllMovies() {
        movieRepository.deleteAll();
    }

    @Override
    public Iterable<Movie> getCustomizedMovieList() {
        Page<Movie> moviePage = movieRepository.findAll(PageRequest.of(0,20, Sort.by("name").ascending()));
        return moviePage.getContent().subList(4,7);
    }

    @Override
    public List<Movie> searchall(String name) {
        return movieRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Page<Movie> pagiantioṇ(Integer page) {
        Pageable pageable=PageRequest.of(page-1,10);
        return movieRepository.findAll(pageable);
    }

    @Override
    public List<Movie> findcategory(String category) {
        List<MovieGenre> movieGenres=movieGenreRepository.findAll();
        List<Movie> _movie=new ArrayList<>();
        for(MovieGenre movieGenre:movieGenres){
            if(movieGenre.getGenre().getName().equals(category)){
                _movie.add(movieGenre.getMovie());
            }
        }
        return _movie;
    }

    @Override
    public List<Movie> trendmovie() {
        return movieRepository.trendmovie();
    }

    @Override
    public List<Movie> recentmovie() {
        return movieRepository.recentmovie();
    }

}
