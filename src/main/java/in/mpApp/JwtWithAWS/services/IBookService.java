package in.mpApp.JwtWithAWS.services;

import in.mpApp.JwtWithAWS.dtos.BookDTO;
import in.mpApp.JwtWithAWS.enums.BookCategory;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

public interface IBookService {

    BookDTO create(final BookDTO bookDTO);
    Optional<BookDTO> updateByISBN(final String isbn, final BookDTO bookDTO) throws DataIntegrityViolationException;
    Optional<BookDTO> getByISBN(final String isbn);
    List<BookDTO> getAllByName(final String name);
    List<BookDTO> getAll();
    List<BookDTO> getAllByCategory(final BookCategory bookCategory);
    void deleteBookByISBN(final Long id);
}
