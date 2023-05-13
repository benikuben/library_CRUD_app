package org.example.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Title should not be empty")
    @Size(min = 1, max = 100, message = "Title should be between 1 and 100 characters")
    @Column(name = "title")
    private String title;
    @NotEmpty(message = "Author should not be empty")
    @Size(min = 1, max = 100, message = "Author's name should be between 1 and 100 characters")
    @Column(name = "author")
    private String author;
    @Min(message = "Year should be greater than 0", value = 1)
    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Transient
    private Boolean greaterThanExpiredDeadline;

    public Book(int id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date ShouldBeReturned() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(createdAt);
        calendar.add(Calendar.DATE, 10);
        System.out.println(calendar.getTime());
        return calendar.getTime();
    }

    public Boolean getGreaterThanExpiredDeadline() {
        return greaterThanExpiredDeadline;
    }

    public void setGreaterThanExpiredDeadline() {
        if (new Date().after(this.ShouldBeReturned())) {
            this.greaterThanExpiredDeadline = true;
        } else this.greaterThanExpiredDeadline = false;
    }
}
