package edu.pa.database.repository;

import edu.pa.database.model.AddressEntity;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;

import java.util.List;

import static org.neo4j.driver.Values.parameters;

public class AddressRepository extends AbstractRepository<AddressEntity>{

    public boolean fullAddress(AddressEntity address){
        try(Session session = driverManager.getSession()) {
            List<Record> records = session.readTransaction(tx -> tx.run("Match (a:Country), (b:County), (c:City) WHERE " +
                    "a.name= $countryName AND b.name= $countyName AND c.name= $cityName AND (b)-[:COUNTY_OF]->(a) AND (c)-[:CITY_OF]->(b) " +
                    "RETURN true", parameters("countryName", address.getCountry().getName(),
                    "countyName", address.getCounty().getName(),
                    "cityName", address.getCity().getName())).list());
            return !records.isEmpty();
        }catch (Exception e){
            e.printStackTrace();
            return false;}
    }

    public boolean countyToCountry(AddressEntity address){
        try(Session session = driverManager.getSession()) {
            List<Record> records = session.readTransaction(tx -> tx.run("Match (a:Country), (b:County) WHERE " +
                "a.name= $countryName AND b.name= $countyName AND (b)-[:COUNTY_OF]->(a) " +
                "RETURN true", parameters("countryName", address.getCountry().getName(),
                "countyName", address.getCounty().getName())).list());
            return !records.isEmpty();
        }catch (Exception e){
        e.printStackTrace();
        return false;}
    }

    public boolean cityToCounty (AddressEntity address){
        try(Session session = driverManager.getSession()) {
            List<Record> records = session.readTransaction(tx -> tx.run("Match (b:County), (c:City) WHERE " +
                    " b.name= $countyName AND c.name= $cityName AND (c)-[:CITY_OF]->(b) " +
                    "RETURN true", parameters("countyName", address.getCounty().getName(),
                    "cityName", address.getCity().getName())).list());
            return !records.isEmpty();
        }catch (Exception e){
            e.printStackTrace();
            return false;}
    }



    @Override
    public boolean save(AddressEntity item) { return false; }
    @Override
    public AddressEntity findByName(String name) { return null; }
}
