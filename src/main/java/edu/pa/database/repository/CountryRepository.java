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
            session.writeTransaction(tx -> tx.run("CREATE (c:Country {name:$name, capital: $capitalName, id:$id})",
                    parameters("name", item.getName(), "capitalName", item.getCapital(),"id", item.getId()) ));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;}
    }

    @Override
    public Country findByName(String name) {
        try(Session session = driverManager.getSession()) {
            List<Record> records = session.readTransaction(tx -> tx.run("MATCH (c:Country) WHERE c.name = $name RETURN c.id, c.capital", parameters("name", name)).list());
            if(!records.isEmpty())
                return new Country(records.get(0).get("c.id").asInt(), name,records.get(0).get("c.capital").asString() );
            else
                return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;}
    }

    public String getCapital (String countryName){
        try {
           Country country = findByName(countryName);
           if(country != null) return country.getCapital();
           else return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;}
    }
}
