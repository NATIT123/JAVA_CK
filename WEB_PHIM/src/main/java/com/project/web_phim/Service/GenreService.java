package com.project.web_phim.Service;

import com.project.web_phim.Model.Genre;
import com.project.web_phim.Repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public interface GenreService{

    public Genre findbyname(String name);
    public Iterable<Genre> getAllGenres();

    public Optional<Genre> getGenre(Long id);

    public Genre updateGenre(Long id,Genre acc);

    public void deleteGenre(Long id);

    public void saveGenre(Genre acc);

    public void deleteAllGenres();

    public Iterable<Genre> getCustomizedGenreList();
}
