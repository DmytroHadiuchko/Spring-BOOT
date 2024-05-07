package dmytro.hadiuchko.springboot.dto.book.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookRequestDto {
    @NotBlank(message = "title may not be blank")
    private String title;
    @NotBlank(message = "Author may not be blank")
    private String author;
    @NotBlank
    @Size(min = 13, message = "ISBN must be at least 13-digit numbers long")
    private String isbn;
    @NotNull(message = "price may not be null")
    @Min(value = 0, message = "min value of price is 0")
    private BigDecimal price;
    private String description;
    private String coverImage;
}
