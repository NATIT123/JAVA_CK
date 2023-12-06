package com.project.web_phim.Service;

import com.project.web_phim.Model.MovieGenre;
import com.project.web_phim.Repository.MovieGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieGenreServiceImp implements MovieGenreService {
    @Autowired
    private MovieGenreRepository movieGenreRepository;

    @Override
    public Iterable<MovieGenre> getAllMovieGenres() {
        return movieGenreRepository.findAll();
    }

    @Override
    public Optional<MovieGenre> getMovieGenre(Long id) {
        return movieGenreRepository.findById(id);
    }

    @Override
    public MovieGenre updateMovieGenre(Long id, MovieGenre acc) {
        return null;
    }

    @Override
    public void deleteMovieGenre(Long id) {
        movieGenreRepository.deleteById(id);
    }

    @Override
    public void saveMovieGenre(MovieGenre acc) {
        movieGenreRepository.save(acc);
    }

    @Override
    public void deleteAllMovieGenres() {
        movieGenreRepository.deleteAll();;
    }

    @Override
    public Iterable<MovieGenre> getCustomizedMovieGenreList() {
        return null;
    }
}
