package edu.pa.database;

import org.neo4j.driver.*;

import java.io.File;
import java.sql.*;

import static org.neo4j.driver.Values.parameters;

public class InsertData {

    public static void main(String[] args) {
    /*    Driver driver = GraphDatabase.driver("bolt://localhost:7687",
                AuthTokens.basic("neo4j", "student"));

        try (Session session = driver.session(SessionConfig.forDatabase("neo4j"))) {
           File f=new File("C:\\Users\\strat\\Desktop\\Romania.json");
            String path="C:\\Users\\strat\\Desktop\\Romania.json";
       //     String windowsPath = "C:\\Users\\strat\\Desktop\\Romania.json";
       //     URI uri = new URI(windowsPath.replace("\\", "/"));
            //URI uri = URI.create(path);
            //File file = new File(uri);
            //URI u=new URI(windowsPath);
          //  URI url=new URI(path);
           // Connection connection=driver
            //PreparedStatement ps=
            String cypherQuery =
                    "CALL apoc.import.json(\""+f.toURI()+"\")\n"+
                            "YIELD value\n" +
                            "MERGE (c:Country {name: value.name })\n" +
                            "WITH c, value\n" +
                            "FOREACH(state in value.states |\n" +
                            "MERGE (s:State {name:state.name})\n" +
                            "MERGE (s)-[:STATE_OF]->(c)\n" +
                            "FOREACH(city in state.cities |\n" +
                            "MERGE(d:City {name:city.name})\n" +
                            "MERGE (d)-[:CITY_OF]->(s)))";

            var result = session.writeTransaction(
                   tx -> tx.run(cypherQuery).list());

           /* for (Record record : result) {
                System.out.println(record.get("product").asString());
            }
            System.out.println("dcswed");
            //System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.close();*/
     //   Config noSSL = Config.build().withEncryptionLevel(Config.EncryptionLevel.NONE).toConfig();
        try (Connection con = DriverManager.getConnection("jdbc:neo4j:bolt://localhost", "neo4j", "student")) {

            // Querying
            String query = "CALL apoc.import.json(\\\"\"+f.toURI()+\"\\\")\\n\"+\n" +
                    "                            \"YIELD value\\n\" +\n" +
                    "                            \"MERGE (c:Country {name: value.name })\\n\" +\n" +
                    "                            \"WITH c, value\\n\" +\n" +
                    "                            \"FOREACH(state in value.states |\\n\" +\n" +
                    "                            \"MERGE (s:State {name:state.name})\\n\" +\n" +
                    "                            \"MERGE (s)-[:STATE_OF]->(c)\\n\" +\n" +
                    "                            \"FOREACH(city in state.cities |\\n\" +\n" +
                    "                            \"MERGE(d:City {name:city.name})\\n\" +\n" +
                    "                            \"MERGE (d)-[:CITY_OF]->(s)))";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
               // stmt.setString(1,"John");

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        System.out.println("Friend: "+rs.getString("f.name")+" is "+rs.getInt("f.age"));
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
