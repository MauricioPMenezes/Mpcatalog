package com.mauriciopm.mpcatalog.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "tb_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty(message="Campo nome Requerido")
    private String name;

    @Column(columnDefinition= "TIMESTAMP WITHOUT TIME ZONE" )
    private Instant createdAt;

    @Column(columnDefinition= "TIMESTAMP WITHOUT TIME ZONE" )
    private Instant updatedAt;

    public Category(){

    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public Instant getCreatedAT() {
        return createdAt;
    }
    public Instant getUpdatedAT() {
        return updatedAt;
    }

    @PrePersist
    public void prePersist(){
        createdAt =Instant.now();
    }

    @PreUpdate
    public void preUpdate(){
        updatedAt =Instant.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
