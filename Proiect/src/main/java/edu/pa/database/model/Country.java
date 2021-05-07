package edu.pa.database.model;


import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.util.List;

@Node("Country")
public class Country {
    @Id
    private int id;

    @Property("name")
    private String name;


    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    //Override

    @Override
    public String toString() {
        return "State " +
                "id: " + id +
                ", name: " + name;
    }
}
