package edu.najah.library.models.interfaces;
import edu.najah.library.models.Book;

import java.util.List;

public interface BookDAO {
    List<Book> findBooksByTitle(String title);
    Book getBookById(int id);
}
