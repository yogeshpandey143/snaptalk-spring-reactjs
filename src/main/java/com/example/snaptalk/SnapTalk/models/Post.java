package com.example.snaptalk.SnapTalk.models;




import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   private int postId;

    private String caption;

    private String image;

    private  String video;

    private LocalDateTime createdDate;


    @JsonIgnore
    @ManyToOne
    private User user;

    @OneToMany
    private List<User> liked= new ArrayList<>();

    public Post(int postId, String caption, String image, String video, LocalDateTime createdDate, User user, List<User> liked) {
        this.postId = postId;
        this.caption = caption;
        this.image = image;
        this.video = video;
        this.createdDate = createdDate;
        this.user = user;
        this.liked = liked;
    }

    public Post() {
    }

    public List<User> getLiked() {
        return liked;
    }

    public void setLiked(List<User> liked) {
        this.liked = liked;
    }

    public int getPostId() {
        return postId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
