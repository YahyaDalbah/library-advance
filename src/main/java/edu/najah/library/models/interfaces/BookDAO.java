package edu.najah.library.models.interfaces;

import edu.najah.library.models.user.Book;

import java.util.List;

public interface BookDAO {
    public void insert(Book book);
    public  List<Book>  getAllBooks();
    public void deleteBookById(int id);
}
