package com.project.web_phim.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@Table(name="movie")
//@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String Status;

    @Column(nullable=false)
    private String releasedYear;

    @Column(nullable=false)
    private String Duration;

    @Column(nullable=false)
    private String Description;

    @Column(nullable=false)
    private Integer numLikes;

    @Column(nullable=false)
    private Integer numViews;

    @Column(nullable=false)
    private Double Rating;

    @Column(nullable=false)
    private String updatedDate;

    @Column(nullable=false)
    private String createdDate;

    @Column(nullable=false)
    private String imgSrc;

    @Column(nullable = false)
    private String nation;

    @OneToMany(mappedBy = "movie",fetch = FetchType.EAGER)
    private List<MovieActor> actorlist;


    @OneToMany(mappedBy = "movie",fetch = FetchType.EAGER)
    private List<MovieGenre> genrelist;

    @OneToMany(mappedBy = "movie",fetch = FetchType.EAGER)
    private List<Episode> episodes;

    @Column(nullable = false)
    private String type;

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", Status='" + Status + '\'' +
                ", releasedYear='" + releasedYear + '\'' +
                ", Duration='" + Duration + '\'' +
                ", Description='" + Description + '\'' +
                ", numLikes=" + numLikes +
                ", numViews=" + numViews +
                ", Rating=" + Rating +
                ", updatedDate='" + updatedDate + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                ", nation='" + nation + '\'' +
                '}';
    }
}
