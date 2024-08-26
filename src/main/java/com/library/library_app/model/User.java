package com.library.library_app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="users_sequence")
    @SequenceGenerator(name = "users_sequence", sequenceName = "users_seq")
    Long id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "surname", nullable = false)
    String surname;

    public User() {
    }

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public User(Long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
