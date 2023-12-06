package com.project.web_phim.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="episode")
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String videoSrc;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable=false)
    private Movie movie;
}
