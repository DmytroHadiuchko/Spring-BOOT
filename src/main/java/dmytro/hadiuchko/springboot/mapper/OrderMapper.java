package dmytro.hadiuchko.springboot.mapper;

import dmytro.hadiuchko.springboot.config.MapperConfig;
import dmytro.hadiuchko.springboot.dto.order.OrderResponseDto;
import dmytro.hadiuchko.springboot.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = OrderItemsMapper.class)
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "orderItems", source = "order.orderItems")
    OrderResponseDto toDto(Order order);
}
