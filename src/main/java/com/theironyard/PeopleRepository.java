package com.theironyard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by chrisaanerud on 4/14/17.
 */
@SuppressWarnings("ALL")
@Component
public class PeopleRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Person> listPeople(String search) {
        return jdbcTemplate.query(
                "SELECT * FROM person WHERE lower(firstname) LIKE lower(?) OR lower(lastname) LIKE lower(?)" + "LIMIT 200",
                new Object[]{"%" + search + "%", "%" + search + "%"},
                (resultSet, i) -> new Person(
                        resultSet.getInt("personid"),
                        resultSet.getString("title"),
                        resultSet.getString("firstname"),
                        resultSet.getString("middlename"),
                        resultSet.getString("lastname"),
                        resultSet.getString("suffix")
                )
        ) ;
    }

    public Person getPerson(Integer personId) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM person WHERE personid =?",
                new Object[]{personId},
                (resultSet, i) -> new Person(
                        resultSet.getInt("personid"),
                        resultSet.getString("title"),
                        resultSet.getString("firstname"),
                        resultSet.getString("middlename"),
                        resultSet.getString("lastname"),
                        resultSet.getString("suffix")
                )
        );
    }

    public void savePerson(Person person) {
        if(person.getPersonId() == null){
            jdbcTemplate.update("INSERT INTO person(title, firstname, middlename, lastname, suffix)" +
                    "VALUES (?,?,?,?,?)",
                    new Object[]{person.getTitle(), person.getFirstName(), person.getMiddleName(), person.getLastName(), person.getSuffix()}

            );
        }else{
jdbcTemplate.update(
        "UPDATE person SET title=?, firstname=?, middlename=?, lastname=?, suffix=? WHERE personid=?",
        new Object[]{person.getTitle(), person.getFirstName(), person.getMiddleName(), person.getLastName(), person.getSuffix(), person.getPersonId()}

);
        }
    }
}
