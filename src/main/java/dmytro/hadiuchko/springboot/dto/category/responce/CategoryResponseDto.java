package dmytro.hadiuchko.springboot.dto.category.responce;

import lombok.Data;

@Data
public class CategoryResponseDto {
    private Long id;
    private String name;
    private String description;
}
