package com.example.instaclone.Model;

public class Comment {
    private String publisher,comment,id;

    public Comment() {
    }

    public Comment(String publisher, String comment, String id) {
        this.publisher = publisher;
        this.comment = comment;
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
