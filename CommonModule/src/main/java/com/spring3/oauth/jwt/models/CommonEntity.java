package com.spring3.oauth.jwt.models;


import jakarta.persistence.*;

@Entity
public class CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
}
