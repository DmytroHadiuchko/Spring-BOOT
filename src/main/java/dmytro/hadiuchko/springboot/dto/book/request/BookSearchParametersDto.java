package dmytro.hadiuchko.springboot.dto.book.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BookSearchParametersDto(
        @NotBlank(message = "title may not be blank")
        String title,
        @NotBlank(message = "author may not be blank")
        String author,
        @Size(min = 13, message = "ISBN must be at least 13-digit numbers long")
        String isbn) {
}
