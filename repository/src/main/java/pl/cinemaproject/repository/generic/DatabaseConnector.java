package pl.cinemaproject.repository.generic;

import org.jdbi.v3.core.Jdbi;

public class DatabaseConnector {

    private final Jdbi jdbi;


    public DatabaseConnector(String url, String username, String password) {

        this.jdbi = Jdbi.create(url, username, password);
    }

    public Jdbi getJdbi() {
        return jdbi;
    }
}
