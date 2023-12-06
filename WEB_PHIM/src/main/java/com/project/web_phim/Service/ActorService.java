package com.project.web_phim.Service;

import com.project.web_phim.Model.Account;
import com.project.web_phim.Model.Actor;

import java.util.Optional;

public interface ActorService {
    public Actor findactorbyname(String name);
    public Iterable<Actor> getAllActors();

    public Optional<Actor> getActor(Long id);

    public Actor updateActor(Long id,Actor acc);

    public void deleteActor(Long id);

    public void saveActor(Actor acc);

    public void deleteAllActors();

    public Iterable<Actor> getCustomizedActorList();
}
