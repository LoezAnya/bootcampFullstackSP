package com.reto.backend.security.entity;

import com.reto.backend.security.enums.ERole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private ERole namerole;

    public Role() {
    }

    public Role( ERole namerole) {

        this.namerole = namerole;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ERole getNamerole() {
        return namerole;
    }

    public void setNamerole(ERole namerole) {
        this.namerole = namerole;
    }
}
