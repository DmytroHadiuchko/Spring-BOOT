package dmytro.hadiuchko.springboot.dto.category.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CategoryRequestDto(
        @NotBlank(message = "name may not be blank")
        @Length(min = 4, max = 16, message = "Name's length should be between 4 and 16")
        String name,
        @NotNull(message = "description may not be null")
        String description) {
}
