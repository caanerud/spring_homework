package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PeopleRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Person> listPeople(String search) {
        return jdbcTemplate.query(
                "SELECT * " +
                        "FROM person " +
                        "WHERE lower(firstname) = lower(?) " +
                        "or lower(lastname) = lower(?)",
                new Object[]{search, search},
                (resultSet, row) -> new Person(
                        resultSet.getInt("personid"),
                        resultSet.getString("title"),
                        resultSet.getString("firstname"),
                        resultSet.getString("middlename"),
                        resultSet.getString("lastname"),
                        resultSet.getString("suffix")
                )
        );
    }
}
