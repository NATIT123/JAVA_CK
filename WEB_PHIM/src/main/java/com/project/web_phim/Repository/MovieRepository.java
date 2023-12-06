package com.project.web_phim.Repository;

import com.project.web_phim.Model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    public List<Movie> findByNameContainingIgnoreCase(String name);

    @Query("SELECT m FROM Movie m order by m.numViews DESC limit 10")
    public List<Movie> trendmovie();

    @Query("SELECT m FROM Movie m order by m.createdDate ASC  limit 10")
    public List<Movie>recentmovie();


}
