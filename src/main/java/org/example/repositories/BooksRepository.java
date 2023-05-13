package org.example.repositories;

import org.example.models.Book;
import org.example.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByPerson(Person person);

    Optional<Person> findByPerson(Person person);

    Page<Book> findAll(Pageable pageable);

    List<Book> findAll(Sort sort);

    List<Book> findByTitleStartingWith(String startingWith);
}
