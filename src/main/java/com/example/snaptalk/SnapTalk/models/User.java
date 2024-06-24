package com.example.snaptalk.SnapTalk.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity

@Table(name="users")
public class User {


    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    private int id;
    @Column(name="firstname")
    private String firstName;
    @Column(name="lastname")
    private String lastName;
    private String email;
    private String password;

    private String gender;


    private List<Integer> followers= new ArrayList<>();


    private List<Integer> following= new ArrayList<>();

    @ManyToMany
    private List<Post> savedPost = new ArrayList<>();


    public User(int id, String firstName, String lastName, String email, String password, String gender, List<Integer> followers, List<Integer> following ) {
       super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.followers = followers;
        this.following = following;

    }

    public User() {
    }

    public List<Post> getSavedPost() {
        return savedPost;
    }



    public void setSavedPost(List<Post> savedPost) {
        this.savedPost = savedPost;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Integer> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Integer> followers) {
        this.followers = followers;
    }

    public List<Integer> getFollowing() {
        return following;
    }

    public void setFollowing(List<Integer> following) {
        this.following = following;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
