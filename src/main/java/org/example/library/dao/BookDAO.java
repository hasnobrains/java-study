package org.example.library.dao;

import org.example.library.models.Book;
import org.example.library.models.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("select * from book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book getBook(int book_id) {
        Book book =  jdbcTemplate.query("select * from book where id=?",
                new BeanPropertyRowMapper<>(Book.class), new Object[]{book_id}).stream().findAny().orElse(null);
        return book;
    }

    public void save(Book book) {
        jdbcTemplate.update("insert into book (title, author, year) values (?, ?, ?)", book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from book where id=?", id);
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("update book set title=?, author=?, year=? where id=?",
                book.getTitle(), book.getAuthor(), book.getYear(), id);
    }

    public Person getPerson(int id) {
        return jdbcTemplate.query("select * from person where id = ?",
                new BeanPropertyRowMapper<>(Person.class),
                new Object[]{id}).stream().findAny().orElse(null);

    }
}
