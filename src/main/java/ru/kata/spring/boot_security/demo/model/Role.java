package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    public Role() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

}