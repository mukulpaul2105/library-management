package in.mpApp.JwtWithAWS.entities;

import in.mpApp.JwtWithAWS.constants.EntityName;
import in.mpApp.JwtWithAWS.constants.TableName;
import in.mpApp.JwtWithAWS.enums.BookCategory;
import in.mpApp.JwtWithAWS.enums.BookStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity(name = EntityName.BOOK)
@Table(name = TableName.BOOK)
public class BookEntity extends IDedEntity {

    @Column(name = "name", columnDefinition = "VARCHAR(100)", nullable = false)
    private String name;

    @Column(name = "isbn", columnDefinition = "VARCHAR(30)", nullable = false, unique = true)
    private String isbn;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookCategory category;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookStatus status;
}
