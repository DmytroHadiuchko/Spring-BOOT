package dmytro.hadiuchko.springboot.controller;

import dmytro.hadiuchko.springboot.dto.request.BookSearchParametersDto;
import dmytro.hadiuchko.springboot.dto.request.CreateBookRequestDto;
import dmytro.hadiuchko.springboot.dto.response.BookDto;
import dmytro.hadiuchko.springboot.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book management", description = "Endpoints for managing books")
@RestController
@RequestMapping(value = "/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Get all products", description = "Get list with sorted products")
    public List<BookDto> getAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get product by id", description = "return book by id")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "create a new book", description = "create a new book")
    public BookDto createBook(@Valid @RequestBody CreateBookRequestDto bookDto) {
        return bookService.save(bookDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update book by id", description = "looking for and update book by id")
    public void updateById(@PathVariable Long id,
                           @Valid @RequestBody CreateBookRequestDto bookRequestDto) {
        bookService.updateById(id, bookRequestDto);
    }

    @GetMapping("/search")
    @Operation(summary = "search book according to search parameters",
            description = "filter and returns books according to search parameters")
    public List<BookDto> search(@Valid BookSearchParametersDto searchParameters,
                                    Pageable pageable) {
        return bookService.search(searchParameters, pageable);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "delete by id", description = "looking for and remove book by id")
    public void deleteBookById(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
