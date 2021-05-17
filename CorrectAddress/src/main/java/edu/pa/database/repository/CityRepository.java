package edu.pa.database.repository;

import edu.pa.database.model.City;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;

import java.util.List;

import static org.neo4j.driver.Values.parameters;

public class CityRepository extends AbstractRepository<City>{
    @Override
    public boolean save(City item) {
        try(Session session = driverManager.getSession()) {
            session.writeTransaction(tx -> tx.run("CREATE (c:City {name:$name, id:$id})", parameters("name", item.getName(), "id", item.getId()) ));
            session.writeTransaction(tx -> tx.run("MATCH " +
                    "(a:City), " +
                    "(b:County) " +
                    "WHERE a.name= $cityName AND a.id= $cityId AND b.name= $county " +
                    "CREATE (a)-[r:CITY_OF]->(b) ", parameters("cityName", item.getName(),"cityId", item.getId(), "county", item.getCounty())));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;}
    }

    @Override
    public City findByName(String name) {
        try(Session session = driverManager.getSession()) {
            List<Record> records = session.readTransaction(tx -> tx.run("MATCH (c:City), (b:County) WHERE c.name = $name" +
                    " AND (c)-[:CITY_OF]->(b) RETURN c.id, b.name ", parameters("name", name)).list());
            if(!records.isEmpty())
            return new City(records.get(0).get("c.id").asInt(), name, records.get(0).get("b.name").asString());
            else return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;}
    }
    public String getCountry(String cityName){
        String country ="";
        try(Session session = driverManager.getSession()) {
            List<Record> records = session.readTransaction(tx -> tx.run("Match (c:City), (b:Country) Where c.name= $cityName "+
                    "AND (c)-[:CITY_OF]->()-[:COUNTY_OF]->(b) Return b.name" , parameters("cityName", cityName)).list());
            country = records.get(0).get("b.name").asString();

        }catch (Exception e){
            e.printStackTrace();
        }
        return country;
    }
}
