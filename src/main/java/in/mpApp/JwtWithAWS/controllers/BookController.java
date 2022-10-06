package in.mpApp.JwtWithAWS.controllers;

import in.mpApp.JwtWithAWS.dtos.BookDTO;
import in.mpApp.JwtWithAWS.enums.BookCategory;
import in.mpApp.JwtWithAWS.models.requests.BookCreationRequest;
import in.mpApp.JwtWithAWS.models.requests.BookUpdateRequest;
import in.mpApp.JwtWithAWS.models.responses.ApiResponse;
import in.mpApp.JwtWithAWS.models.responses.BookCreatedResponse;
import in.mpApp.JwtWithAWS.models.responses.ErrorCustom;
import in.mpApp.JwtWithAWS.services.IBookService;
import in.mpApp.JwtWithAWS.utils.DataMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private IBookService bookService;

    @PostMapping
    public ResponseEntity<ApiResponse<BookCreatedResponse>> create(@Validated @RequestBody final BookCreationRequest bookCreationRequest) {
        log.info("Received Book Creation Request: {} ", bookCreationRequest);
        BookDTO bookDTO = DataMapperUtil.convertTo(bookCreationRequest, BookDTO.class);
        bookDTO = bookService.create(bookDTO);
        log.info("Created Book: {} ", bookDTO);
        return ResponseEntity.ok(ApiResponse.success(DataMapperUtil.convertTo(bookDTO, BookCreatedResponse.class)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookCreatedResponse>>> getAll() {
        log.info("Received Request to Fetch all Books");
        List<BookCreatedResponse> bookDTOList = new ArrayList<>();
        bookService.getAll().forEach((be) -> {
            bookDTOList.add(DataMapperUtil.convertTo(be, BookCreatedResponse.class));
        });
        return ResponseEntity.ok(ApiResponse.success(bookDTOList));
    }

    @PutMapping(params = "isbn")
    public ResponseEntity<ApiResponse<BookCreatedResponse>> update(@RequestParam("isbn") final String isbn, @Validated @RequestBody final BookUpdateRequest bookCreationRequest) {
        log.info("Received ISBN: {} , Book Details: {} ", isbn, bookCreationRequest);
        BookDTO bookDTO = DataMapperUtil.convertTo(bookCreationRequest, BookDTO.class);
        bookDTO = bookService.updateByISBN(isbn, bookDTO).orElseThrow(() -> new RuntimeException("Not book found"));
        log.info("Updated Book: {} ", bookDTO);
        return ResponseEntity.ok(ApiResponse.success(DataMapperUtil.convertTo(bookDTO, BookCreatedResponse.class)));
    }

    @GetMapping(params = "name")
    public ResponseEntity<ApiResponse<List<BookCreatedResponse>>> getAllByName(@RequestParam("name") final String name) {
        log.info("Received Request to Fetch all Books by Name: {} ", name);
        List<BookCreatedResponse> bookDTOList = new ArrayList<>();
        bookService.getAllByName(name).forEach((be) -> {
            bookDTOList.add(DataMapperUtil.convertTo(be, BookCreatedResponse.class));
        });
        log.info("Retrieved all Books by Name: {} ", bookDTOList);
        return ResponseEntity.ok(ApiResponse.success(bookDTOList));
    }

    @GetMapping(params = "isbn")
    public ResponseEntity<ApiResponse<BookCreatedResponse>> getByISBN(@RequestParam("isbn") final String isbn) {
        log.info("Received Request to Fetch all Books");
        final Optional<BookDTO> bookDTO = bookService.getByISBN(isbn);
        if(bookDTO.isPresent()) {
            log.info("Retrieved Book By ISBN: {} ", bookDTO.get());
            return ResponseEntity.ok(ApiResponse.success(DataMapperUtil.convertTo(bookDTO.get(), BookCreatedResponse.class)));
        }
        return ResponseEntity.badRequest().body(ApiResponse.error(ErrorCustom.create("ERR-0000", "Some Error occured")));
    }

    @GetMapping(params = "category")
    public ResponseEntity<ApiResponse<List<BookCreatedResponse>>> getAllByCategory(@RequestParam("category") final BookCategory category) {
        log.info("Received Request to Fetch all Books by Category: {} ", category);
        List<BookCreatedResponse> bookDTOList = new ArrayList<>();
        bookService.getAllByCategory(category).forEach((be) -> {
            bookDTOList.add(DataMapperUtil.convertTo(be, BookCreatedResponse.class));
        });
        log.info("Retrieved all Books by Category: {} ", bookDTOList);
        return ResponseEntity.ok(ApiResponse.success(bookDTOList));
    }

    @DeleteMapping(params = "isbn")
    public ResponseEntity<ApiResponse<String>> deleteBook(@RequestParam("isbn") final String isbn) {
        log.info("Received Remove Book Request: {} ", isbn);
        Optional<BookDTO> bookDTO = bookService.getByISBN(isbn);
        if(bookDTO.isPresent()) {
            bookService.deleteBookByISBN(bookDTO.get().getId());
            return ResponseEntity.ok(ApiResponse.success(" Book has been removed"));
        }
        return ResponseEntity.badRequest().body(ApiResponse.error(ErrorCustom.create("ERR-0001", "Book Not found")));
    }

}
