package com.project.web_phim.Repository;

import com.project.web_phim.Model.MovieActor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieActorRepository extends JpaRepository<MovieActor,Long> {
}
