package in.mpApp.JwtWithAWS.repositories;

import in.mpApp.JwtWithAWS.entities.BookEntity;
import in.mpApp.JwtWithAWS.enums.BookCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends IDedRepository<BookEntity> {

    Optional<BookEntity> findByIsbn(final String isbn);
    List<BookEntity> findByName(final String isbn);

    List<BookEntity> findByCategory(final BookCategory category);
}
