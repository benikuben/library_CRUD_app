package org.example.services;


import jakarta.persistence.EntityManager;
import org.example.models.Book;
import org.example.models.Person;
import org.example.repositories.BooksRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final EntityManager entityManager;

    @Autowired
    public BooksService(BooksRepository booksRepository, EntityManager entityManager) {
        this.booksRepository = booksRepository;
        this.entityManager = entityManager;
    }

    public List<Book> findAll(String page, String booksPerPage, String sortByYear) {
        if (page != null && booksPerPage != null)
            if (Boolean.valueOf(sortByYear))
                return booksRepository.findAll(PageRequest.of(Integer.parseInt(page),
                        Integer.parseInt(booksPerPage),
                        Sort.by("year"))).getContent();
            else
                return booksRepository.findAll(PageRequest.of(Integer.parseInt(page), Integer.parseInt(booksPerPage))).getContent();
        else if (Boolean.valueOf(sortByYear)) return booksRepository.findAll(Sort.by("year"));
        return booksRepository.findAll();
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void save(Book Book) {
        booksRepository.save(Book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void update(int bookId, int personId) {
        Session session = entityManager.unwrap(Session.class);
        Book book = session.get(Book.class, bookId);
        book.setPerson(session.get(Person.class, personId));
        book.setCreatedAt(new Date());
        booksRepository.save(book);
    }

    @Transactional
    public void update(int bookId) {
        Session session = entityManager.unwrap(Session.class);
        Book book = session.get(Book.class, bookId);
        book.setPerson(null);
        book.setCreatedAt(null);
        booksRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public List<Book> findByPerson(Person person) {
        List<Book> books = booksRepository.findAllByPerson(person);
        for (Book book : books) {
            if (book.getCreatedAt() != null)
                book.setGreaterThanExpiredDeadline();
        }
        ;
        return books;
    }

    public Person findPersonById(int id) {
        Session session = entityManager.unwrap(Session.class);
        Book book = session.get(Book.class, id);
        return book.getPerson();
    }

    public List<Book> findByTitleStartingWith(String startingWith) {
        return booksRepository.findByTitleStartingWith(startingWith);
    }

}
