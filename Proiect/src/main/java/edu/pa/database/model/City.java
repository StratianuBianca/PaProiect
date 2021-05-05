package edu.pa.database.model;


import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class City {
    private int id;
    private String name;
    private long latitude;
    private long longitude;

    public City() {
    }

    @Basic
    @Column
    public long getLongitude() {
        return longitude;
    }
    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column
    public long getLatitude() {
        return latitude;
    }
    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Id
    @Column
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
