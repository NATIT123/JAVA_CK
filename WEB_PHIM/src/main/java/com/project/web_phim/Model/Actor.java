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

@Table(name="actor")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(nullable=false,name="name")
    private String name;

    @Column(nullable=false)
    private String Nationality;

    @Column(nullable=false)
    private String dateOfBirth;

    @Column(nullable=false)
    private String Gender;

    @OneToMany(mappedBy = "actor",fetch = FetchType.EAGER)
    private List<MovieActor> actorList;

    @Override
    public String toString() {
        return "Actor{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", Nationality='" + Nationality + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", Gender='" + Gender + '\'' +
                ", actorList=" + actorList +
                '}';
    }
}
