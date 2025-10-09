package io.github.CaicoSantos1998.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Table
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String login;

    @Column
    private String password;

    @Column(name = "roles")
    private List<String> roles;
    
}
