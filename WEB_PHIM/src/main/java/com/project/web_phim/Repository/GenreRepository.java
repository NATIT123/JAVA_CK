package com.project.web_phim.Repository;

import com.project.web_phim.Model.Actor;
import com.project.web_phim.Model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre,Long> {
    public Genre findGenreByName (String name);
}
