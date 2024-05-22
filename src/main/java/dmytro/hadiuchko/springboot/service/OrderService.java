package dmytro.hadiuchko.springboot.service;

import dmytro.hadiuchko.springboot.dto.order.CreateOrderRequestDto;
import dmytro.hadiuchko.springboot.dto.order.OrderResponseDto;
import dmytro.hadiuchko.springboot.dto.order.UpdateOrderStatusRequestDto;
import dmytro.hadiuchko.springboot.dto.orderitem.OrderItemResponseDto;
import java.util.List;

public interface OrderService {
    OrderResponseDto createOrder(CreateOrderRequestDto requestDto, Long userId);

    OrderResponseDto updateOrder(Long userId, Long orderId, UpdateOrderStatusRequestDto requestDto);

    List<OrderResponseDto> getAllOrdersForUserById(Long userId);

    List<OrderItemResponseDto> getOrderItemsByOrdersId(Long orderId, Long userId);

    OrderItemResponseDto getOrderItemById(Long orderItemId, Long userId, Long orderId);
}
