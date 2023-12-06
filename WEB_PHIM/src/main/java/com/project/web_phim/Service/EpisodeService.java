package com.project.web_phim.Service;



import com.project.web_phim.Model.Episode;

import java.util.Optional;

public interface EpisodeService {
    public Iterable<Episode> getAllEpisodes();

    public Optional<Episode> getEpisode(Long id);

    public Episode updateEpisode(Long id,Episode ep);

    public void deleteEpisode(Long id);

    public void saveEpisode(Episode ep);

    public void deleteAllEpisodes();

    public Iterable<Episode> getCustomizedEpisodeList();
}
