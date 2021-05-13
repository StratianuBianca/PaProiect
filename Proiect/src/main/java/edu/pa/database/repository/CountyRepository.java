package edu.pa.database.repository;

import edu.pa.database.model.City;
import edu.pa.database.model.County;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;

import java.util.List;

import static org.neo4j.driver.Values.parameters;

public class CountyRepository extends AbstractRepository<County>{
    @Override
    public boolean save(County item) {
        try(Session session = driverManager.getSession()) {
            session.writeTransaction(tx -> tx.run("CREATE (c:County {name:$name, id:$id, countyCapital: $countyCapital})", parameters("name", item.getName(), "id", item.getId(), "countyCapital", item.getCountyCapital()) ));
            session.writeTransaction(tx -> tx.run("MATCH " +
                    "  (a:County),"+
                    "  (b:Country) " +
                    "WHERE a.name = $countyName AND b.name = $countryName " +
                    "CREATE (a)-[r:COUNTY_OF]->(b) ", parameters("countyName", item.getName(), "countryName", item.getCountry())));

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;}
    }

    @Override
    public County findByName(String name) {
        try(Session session = driverManager.getSession()) {
            List<Record> records = session.readTransaction(tx -> tx.run("MATCH (c:County) WHERE c.name = $name RETURN c.id", parameters("name", name)).list());
          if(!records.isEmpty())
            return new County(records.get(0).get("c.id").asInt(), name);
          else
              return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;}
    }

}
