package com.example.snaptalk.SnapTalk.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String content;


    @ManyToOne
    private User user;

    @ManyToMany
    private List<User> liked = new ArrayList<>();

    private LocalDateTime createdAt;



}
