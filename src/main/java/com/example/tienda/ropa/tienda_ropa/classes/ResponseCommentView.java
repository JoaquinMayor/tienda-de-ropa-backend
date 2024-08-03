package com.example.tienda.ropa.tienda_ropa.classes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.tienda.ropa.tienda_ropa.entities.Comment;

public class ResponseCommentView {
    
    Set<Comment> comments;
    List<String> listUsersNames;
   
    public ResponseCommentView() {
        this.comments = new HashSet<>();
        this.listUsersNames = new ArrayList<>();
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public List<String> getListUsersNames() {
        return listUsersNames;
    }

    public void setListUsersNames(List<String> listUsersNames) {
        this.listUsersNames = listUsersNames;
    }

    

    
}
