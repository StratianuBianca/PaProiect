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

    @Property("capital")
    private String capital;


    public Country(int id, String name, String capital) {
        this.id = id;
        this.name = name;
        this.capital = capital;
    }

    //Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCapital() { return capital; }
    public void setCapital(String capital) { this.capital = capital; }

    //Override

    @Override
    public String toString() {
        return "State " +
                "id: " + id +
                ", name: " + name;
    }
}
