package edu.pa.database.repository;

import edu.pa.database.resource.DriverManager;

public abstract class AbstractRepository<T> {
   protected final DriverManager driverManager = new DriverManager();
   abstract public boolean save (T item);
   abstract public T findByName (String name);
}
