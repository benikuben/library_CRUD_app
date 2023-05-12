package org.example.services;


import jakarta.persistence.EntityManager;
import org.example.models.Book;
import org.example.models.Person;
import org.example.repositories.BooksRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Book>  findAll (){
        return booksRepository.findAll();
    }

    public Book findOne(int id){
        Optional<Book> foundBook=booksRepository.findById(id);
        return foundBook.orElse(null);
    }
    @Transactional
    public void save(Book Book){
        booksRepository.save(Book);
    }
    @Transactional
    public void update(int id, Book updatedBook){
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }
    @Transactional
    public void update(int bookId, int personId){
        Session session=entityManager.unwrap(Session.class);
        Book book = session.get(Book.class, bookId);
        book.setPerson(session.get(Person.class, personId));
        booksRepository.save(book);
    }
    @Transactional
    public void update(int bookId){
        Session session=entityManager.unwrap(Session.class);
        Book book = session.get(Book.class, bookId);
        book.setPerson(null);
        booksRepository.save(book);
    }
    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    public List<Book> findByPerson(Person person) {
        return booksRepository.findAllByPerson(person);
    }
    public Person findPersonById(int id){
        Session session=entityManager.unwrap(Session.class);
        Book book = session.get(Book.class, id);
        return book.getPerson();
    }
}
