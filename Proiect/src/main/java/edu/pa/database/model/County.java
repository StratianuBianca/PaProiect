package edu.pa.database.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("County")
public class County {
    @Id
    private int id;

    @Property("name")
    private String name;

    @Relationship(type = "COUNTY_OF")
    String country;

    public County(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public County(int id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    //Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    //Override
    @Override
    public String toString() {
        return "County " +
                "id: " + id +
                ", name: " + name;
    }
}
