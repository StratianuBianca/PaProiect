package edu.pa.database.model;


import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("City")
public class City {
    private @Id int id;

    @Property("name")
    private String name;

    @Relationship(type="CITY_OF")
    String county;

    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public City(int id, String name, String county) {
        this.id = id;
        this.name = name;
        this.county = county;
    }

    //Getters & Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getCounty() { return county; }
    public void setCounty(String county) { this.county = county; }

    //Override

    @Override
    public String toString() {
        return "City " +
                "id: " + id +
                ", name: " + name ;
    }
}
