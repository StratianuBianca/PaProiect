package edu.pa.database.repository;

import edu.pa.database.model.Country;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;

import java.util.List;

import static org.neo4j.driver.Values.parameters;

public class CountryRepository extends AbstractRepository<Country>{
    @Override
    public boolean save(Country item) {
        try(Session session = driverManager.getSession()) {
            session.writeTransaction(tx -> tx.run("CREATE (c:Country {name:$name, id:$id})", parameters("name", item.getName(), "id", item.getId()) ));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;}
    }

    @Override
    public Country findByName(String name) {
        try(Session session = driverManager.getSession()) {
            List<Record> records = session.readTransaction(tx -> tx.run("MATCH (c:Country) WHERE c.name = $name RETURN c.id", parameters("name", name)).list());
            return new Country(records.get(0).get("s.id").asInt(), name);
        }catch (Exception e){
            e.printStackTrace();
            return null;}
    }
}
