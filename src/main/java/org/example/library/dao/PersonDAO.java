package org.example.library.dao;

import org.example.library.models.Book;
import org.example.library.models.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person getPerson(int personId) {
        Person person = jdbcTemplate.query("select * from person where id = ?",
                new BeanPropertyRowMapper<>(Person.class), new Object[]{personId}).stream().findAny().orElse(null);
        person.setBooks(getBooks(personId));
        return person;
    }

    public void save(Person person) {
        jdbcTemplate.update("insert into person(full_name, year_of_birth) values (?, ?)", person.getFullName(), person.getYearOfBirth());
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from person where id=?", id);
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("update person set full_name=?, year_of_birth=? where id=?", person.getFullName(), person.getYearOfBirth(), id);
    }

    public List<Book> getBooks(int personId) {
        return jdbcTemplate.query("select title, author, year from book where person_id =?",
                new BeanPropertyRowMapper<>(Book.class), new Object[]{personId});
    }
}
