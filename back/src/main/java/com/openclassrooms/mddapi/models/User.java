package com.openclassrooms.mddapi.models;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@Data
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    private String password;
    @OneToMany(mappedBy = "user")
    private List<Subscription> subscriptions;
    @OneToMany(mappedBy = "user")

    private List<Comment> comments;
    @OneToMany(mappedBy = "user")
    private List<Article> articles;
}
