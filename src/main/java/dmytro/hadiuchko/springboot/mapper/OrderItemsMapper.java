package dmytro.hadiuchko.springboot.mapper;

import dmytro.hadiuchko.springboot.config.MapperConfig;
import dmytro.hadiuchko.springboot.dto.orderitem.OrderItemResponseDto;
import dmytro.hadiuchko.springboot.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemsMapper {
    @Mapping(target = "bookId", source = "book.id")
    OrderItemResponseDto toDto(OrderItem orderItem);
}
