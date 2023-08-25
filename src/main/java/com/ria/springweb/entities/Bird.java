package com.ria.springweb.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

@Entity
public class Bird {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NotNull
    private int id;

    @NotNull
    private String name;

    @NotNull
    private String family;

    @NotNull
    private ArrayList<String> continents = new ArrayList<>();
    private String added;
    private boolean visible;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public ArrayList<String> getContinents() {
        return continents;
    }

    public void setContinents(ArrayList<String> continents) {
        this.continents = continents;
    }
    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public Boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Bird() {
    }

    public Bird(int id, String name, String family, ArrayList<String> continents, String added, boolean visible) {
        this.id = id;
        this.name = name;
        this.family = family;
        this.continents=continents;
        this.added = added;
        this.visible = visible;
    }
}
