package edu.pa.database.repository;

import edu.pa.database.model.AddressEntity;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;

import java.util.ArrayList;
import java.util.List;

import static org.neo4j.driver.Values.parameters;

public class AddressRepository extends AbstractRepository<AddressEntity>{

    public boolean fullAddress(AddressEntity address){
        try(Session session = driverManager.getSession()) {
            List<Record> records = session.readTransaction(tx -> tx.run("Match (a:Country), (b:County), (c:City) WHERE " +
                    "a.name= $countryName AND b.name= $countyName AND c.name= $cityName AND (b)-[:COUNTY_OF]->(a) AND (c)-[:CITY_OF]->(b) " +
                    "RETURN true", parameters("countryName", address.getCountry(),
                    "countyName", address.getCounty(),
                    "cityName", address.getCity())).list());
            return !records.isEmpty();
        }catch (Exception e){
            e.printStackTrace();
            return false;}
    }

    public boolean countyToCountry(AddressEntity address){
        try(Session session = driverManager.getSession()) {
            List<Record> records = session.readTransaction(tx -> tx.run("Match (a:Country), (b:County) WHERE " +
                "a.name= $countryName AND b.name= $countyName AND (b)-[:COUNTY_OF]->(a) " +
                "RETURN true", parameters("countryName", address.getCountry(),
                "countyName", address.getCounty())).list());
            return !records.isEmpty();
        }catch (Exception e){
        e.printStackTrace();
        return false;}
    }

    public boolean cityToCounty (AddressEntity address){
        try(Session session = driverManager.getSession()) {
            List<Record> records = session.readTransaction(tx -> tx.run("Match (b:County), (c:City) WHERE " +
                    " b.name= $countyName AND c.name= $cityName AND (c)-[:CITY_OF]->(b) " +
                    "RETURN true", parameters("countyName", address.getCounty(),
                    "cityName", address.getCity())).list());
            return !records.isEmpty();
        }catch (Exception e){
            e.printStackTrace();
            return false;}
    }
    public List<String> cityToCountry (AddressEntity address){
        List<String> counties = new ArrayList<>();
        try(Session session = driverManager.getSession()) {
            List<Record> records = session.readTransaction(tx -> tx.run("Match (b:Country), (c:City), (a:County) WHERE " +
                    " b.name= $countryName AND c.name= $cityName AND (c)-[:CITY_OF]->(a) And (a)-[:COUNTY_OF]->(b) " +
                   " RETURN a.name" , parameters("countryName", address.getCountry(),
                    "cityName", address.getCity())).list());
            for(Record record : records){
                counties.add(record.get("a.name").asString());
            }
        }catch (Exception e){
            e.printStackTrace();
            }
        return counties;

    }

    public List <String> nameLike(String possibleCity){
        List<String> names = new ArrayList<>();
        try(Session session = driverManager.getSession()) {
            List<Record> records = session.readTransaction(tx -> tx.run("Match (c) WHERE c.name =~ '.*" + possibleCity + ".*' Return c.name").list());
            for(Record record : records){
                names.add(record.get("c.name").asString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return names;
    }



    @Override
    public boolean save(AddressEntity item) { return false; }
    @Override
    public AddressEntity findByName(String name) { return null; }
}
