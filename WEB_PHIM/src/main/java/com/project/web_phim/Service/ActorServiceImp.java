package com.project.web_phim.Service;

import com.project.web_phim.Model.Account;
import com.project.web_phim.Model.Actor;
import com.project.web_phim.Repository.AccountRepository;
import com.project.web_phim.Repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActorServiceImp implements ActorService{
    @Autowired
    private ActorRepository actorRepository;

    @Override
    public Iterable<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    @Override
    public Optional<Actor> getActor(Long id) {
        return actorRepository.findById(id);
    }

    @Override
    public Actor updateActor(Long id, Actor acc) {
        Actor _acc = actorRepository.findById(id).orElse(null);
        if(acc == null){
            return null;
        }
        _acc.setActorList(acc.getActorList());
        _acc.setName(acc.getName());
        _acc.setGender(acc.getGender());
        _acc.setNationality(acc.getNationality());
        _acc.setGender(acc.getGender());
        return actorRepository.save(_acc);
    }

    @Override
    public void deleteActor(Long id) {
        actorRepository.deleteById(id);
    }

    @Override
    public void saveActor(Actor acc) {
        actorRepository.save(acc);
    }

    @Override
    public void deleteAllActors() {
        actorRepository.deleteAll();
    }

    @Override
    public Iterable<Actor> getCustomizedActorList() {
        Page<Actor> accPage = actorRepository.findAll(PageRequest.of(0,20, Sort.by("username").ascending()));
        return accPage.getContent().subList(4,7);
    }

    @Override
    public Actor findactorbyname(String name) {
        return actorRepository.findActorByName(name);
    }
}
