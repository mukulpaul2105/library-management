package in.mpApp.JwtWithAWS.services.impls;

import in.mpApp.JwtWithAWS.dtos.BookDTO;
import in.mpApp.JwtWithAWS.entities.BookEntity;
import in.mpApp.JwtWithAWS.enums.BookCategory;
import in.mpApp.JwtWithAWS.enums.BookStatus;
import in.mpApp.JwtWithAWS.repositories.BookRepository;
import in.mpApp.JwtWithAWS.services.IBookService;
import in.mpApp.JwtWithAWS.utils.DataMapperUtil;
import in.mpApp.JwtWithAWS.utils.LoggedInContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
public class DefaultBookService implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    @Transactional
    public BookDTO create(BookDTO bookDTO) {
        log.info("Creating Book with bookDTO: {} ", bookDTO);
        BookEntity bookEntity = DataMapperUtil.convertTo(bookDTO, BookEntity.class);
        bookEntity.setCreatedOn(LocalDateTime.now());
        bookEntity.setCreatedBy(LoggedInContext.getCurrentUser());
        bookEntity.setStatus(BookStatus.AVAILABLE);
        bookEntity = bookRepository.save(bookEntity);
        log.info("Saved Book: {} ", bookEntity);
        return DataMapperUtil.convertTo(bookEntity, BookDTO.class);
    }

    @Override
    @Transactional(isolation =  Isolation.REPEATABLE_READ)
    public Optional<BookDTO> updateByISBN(String isbn, BookDTO bookDTO) throws DataIntegrityViolationException {
        log.info("Updating Book: {} BookDTO by ISBN: {} ", bookDTO, isbn);
        final Optional<BookEntity> bookEntityOptional =  bookRepository.findByIsbn(isbn);
        if(bookEntityOptional.isPresent()) {
            log.info("Retrieved Book By ISBN: {} ", bookEntityOptional.get());
            final BookEntity bookEntity = bookEntityOptional.get();
            if(bookDTO.getIsbn() != null) {
                bookEntity.setIsbn(bookDTO.getIsbn());
            }
            if(bookDTO.getName() != null) {
                bookEntity.setName(bookDTO.getName());
            }
            if(bookDTO.getStatus() != null) {
                bookEntity.setStatus(bookDTO.getStatus());
            }
            bookEntity.setUpdatedBy(LoggedInContext.getCurrentUser());
            bookEntity.setUpdatedOn(LocalDateTime.now());
            return Optional.of(DataMapperUtil.convertTo(bookEntity, BookDTO.class));
        }
        return Optional.empty();
    }

    @Override
    public Optional<BookDTO> getByISBN(String isbn) {
        log.info("Retrieving Book by ISBN: {} ", isbn);
        final Optional<BookEntity> book = bookRepository.findByIsbn(isbn);
        log.info("Retrieved Book By ISBN: {} ", book);
        return Optional.of(DataMapperUtil.convertTo(book.get(), BookDTO.class));
    }

    @Override
    public List<BookDTO> getAllByName(String name) {
        log.info("Retrieving Book By Name: {} ", name);
        final List<BookDTO> bookDTOList = new ArrayList<>();
        bookRepository.findByName(name).forEach((be)-> {
            bookDTOList.add(DataMapperUtil.convertTo(be, BookDTO.class));
        });
        log.info("Retrieved All Books by name: {} ", bookDTOList);
        return bookDTOList;
    }

    @Override
    public List<BookDTO> getAll() {
        log.info("Retrieving Book: ");
        final List<BookDTO> bookDTOList = new ArrayList<>();
        bookRepository.findAll().forEach((be)-> {
            bookDTOList.add(DataMapperUtil.convertTo(be, BookDTO.class));
        });
        log.info("Retrieved All Books: {} ", bookDTOList);
        return bookDTOList;
    }

    @Override
    public List<BookDTO> getAllByCategory(BookCategory bookCategory) {
        log.info("Retrieving Book By Category: {} ", bookCategory);
        final List<BookDTO> bookDTOList = new ArrayList<>();
        bookRepository.findByCategory(bookCategory).forEach((be)-> {
            bookDTOList.add(DataMapperUtil.convertTo(be, BookDTO.class));
        });
        log.info("Retrieved All Books by Category: {} ", bookDTOList);
        return bookDTOList;
    }


    @Override
    public void deleteBookByISBN(final Long id) {
        log.info("Deleting Book by Id {} ", id);
        bookRepository.deleteById(id);
    }
}
