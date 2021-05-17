package edu.pa.database.resource;

import org.neo4j.driver.*;

//Return valid sessions to the database
public class DriverManager {
    private final DatabaseConfig databaseConfig = new DatabaseConfig();

    private final Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "student" ) );
    private String database(){ return databaseConfig.databaseSelectionProvider().getDatabaseSelection().getValue();}

    public Session getSession(){
        if(database() == null){
            return driver.session();
        }
        return driver.session(SessionConfig.forDatabase(database()));
    }
}
