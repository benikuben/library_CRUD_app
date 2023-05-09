package org.example.dao;

import org.example.models.Book;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book (title, author, year) VALUES( ?,?,?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id=?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).
                stream().findAny().orElse(null);
    }

    public void update(int book_id) {
        jdbcTemplate.update("UPDATE Book SET person_id=null WHERE id=?",
                book_id);
    }

    public void update(int book_id, int person_id) {
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE id=?",
                person_id, book_id);
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE Book SET title=?,author=?,year=? WHERE id=?",
                book.getTitle(), book.getAuthor(), book.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book",
                new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Person> personTakesBook(int id) {
        return jdbcTemplate.query("SELECT p.* FROM Person p JOIN Book b on p.id=b.person_id and b.id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

}
