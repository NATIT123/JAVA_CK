package com.project.web_phim.Service;


import com.project.web_phim.Model.Episode;
import com.project.web_phim.Repository.EpisodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EpisodeServiceImp implements EpisodeService{

    @Autowired
    private EpisodeRepository episodeRepository;

    @Override
    public Iterable<Episode> getAllEpisodes() {
        return episodeRepository.findAll();
    }

    @Override
    public Optional<Episode> getEpisode(Long id) {
        return episodeRepository.findById(id);
    }

    @Override
    public Episode updateEpisode(Long id, Episode genre) {
        Optional<Episode> optionalEpisode = getEpisode(id);
        Episode ep = new Episode();
        if(optionalEpisode.isPresent()){
            ep = optionalEpisode.get();
        }
        ep.setName(genre.getName());
        return episodeRepository.save(ep);
    }

    @Override
    public void deleteEpisode(Long id) {
        episodeRepository.deleteById(id);
    }

    @Override
    public void saveEpisode(Episode ep) {
        episodeRepository.save(ep);
    }

    @Override
    public void deleteAllEpisodes() {
        episodeRepository.deleteAll();;
    }

    @Override
    public Iterable<Episode> getCustomizedEpisodeList() {
        return null;
    }
}
