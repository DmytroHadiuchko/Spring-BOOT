package dmytro.hadiuchko.springboot.dto.category.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryRequestDto(
        @NotBlank(message = "name may not be blank")
        String name,
        @NotNull(message = "description may not be null")
        String description) {
}
