package com.project.web_phim.Model;

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
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
@Table(name="genre")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(nullable=false)
    private String name;

    @OneToMany(mappedBy = "genre",fetch = FetchType.EAGER)
    private List<MovieGenre> genreList;

    @Override
    public String toString() {
        return "Genre{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", genreList=" + genreList +
                '}';
    }
}
