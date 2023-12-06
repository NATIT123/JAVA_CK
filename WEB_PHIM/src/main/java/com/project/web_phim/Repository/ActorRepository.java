package com.project.web_phim.Repository;

import com.project.web_phim.Model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor,Long> {
    public Actor findActorByName(String name);
}
