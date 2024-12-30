package edu.najah.library.models.interfaces;

import edu.najah.library.models.Book;

import java.util.List;

public interface BookDAO {
    public void insert(Book book);

    public  List<Book>  getAllBooks();
    public void deleteBookById(int id);
    public List<Book> findBooksByTitle(String title);
    public void updateBook(Book book) ;
    public Book getBookById(int id);

}

