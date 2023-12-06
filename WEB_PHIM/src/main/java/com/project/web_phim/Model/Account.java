package com.project.web_phim.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable = false)
    private String fullname;

    @Column(nullable=false)
    private String image;

    @Column(nullable=false)
    private String password;

    @Column(nullable=false, length = 20)
    private String username;

    @Column(nullable=false, length = 5)
    private String role;

    @Column(nullable = false)
    private String createdAt;

    @Column(nullable = false)
    private String updatedAt;


}
