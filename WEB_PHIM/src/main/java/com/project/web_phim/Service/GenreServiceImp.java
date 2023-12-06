package com.project.web_phim.Service;

import com.project.web_phim.Model.Account;
import com.project.web_phim.Model.Genre;
import com.project.web_phim.Repository.AccountRepository;
import com.project.web_phim.Repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GenreServiceImp implements GenreService{

    @Autowired
    private GenreRepository genreRepository;


    @Override
    public Genre findbyname(String name) {
        return genreRepository.findGenreByName(name);
    }

    @Override
    public Iterable<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> getGenre(Long id) {
        return genreRepository.findById(id);
    }

    @Override
    public Genre updateGenre(Long id, Genre genre) {
        Optional<Genre>optionalGenre=getGenre(id);
        Genre _genre=new Genre();
        if(optionalGenre.isPresent()){
            _genre=optionalGenre.get();
        }
        _genre.setName(genre.getName());
        _genre.setGenreList(genre.getGenreList());
        return genreRepository.save(_genre);
    }

    @Override
    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }

    @Override
    public void saveGenre(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    public void deleteAllGenres() {
        genreRepository.deleteAll();;
    }

    @Override
    public Iterable<Genre> getCustomizedGenreList() {
        return null;
    }
}
