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
                    "WHERE a.name= $cityName AND b.name= $county " +
                    "CREATE (a)-[r:CITY_OF]->(b) ", parameters("cityName", item.getName(), "county", item.getCounty())));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;}
    }

    @Override
    public City findByName(String name) {
        try(Session session = driverManager.getSession()) {
            List<Record> records = session.readTransaction(tx -> tx.run("MATCH (c:City) WHERE c.name = $name RETURN c.id", parameters("name", name)).list());
            return new City(records.get(0).get("c.id").asInt(), name);
        }catch (Exception e){
            e.printStackTrace();
            return null;}
    }
}
