package dmytro.hadiuchko.springboot.service.impl;

import dmytro.hadiuchko.springboot.dto.order.CreateOrderRequestDto;
import dmytro.hadiuchko.springboot.dto.order.OrderResponseDto;
import dmytro.hadiuchko.springboot.dto.order.UpdateOrderStatusRequestDto;
import dmytro.hadiuchko.springboot.dto.orderitem.OrderItemResponseDto;
import dmytro.hadiuchko.springboot.entity.CartItem;
import dmytro.hadiuchko.springboot.entity.Order;
import dmytro.hadiuchko.springboot.entity.OrderItem;
import dmytro.hadiuchko.springboot.entity.ShoppingCart;
import dmytro.hadiuchko.springboot.enums.Status;
import dmytro.hadiuchko.springboot.exception.EntityNotFoundException;
import dmytro.hadiuchko.springboot.mapper.OrderItemsMapper;
import dmytro.hadiuchko.springboot.mapper.OrderMapper;
import dmytro.hadiuchko.springboot.repository.OrderItemsRepository;
import dmytro.hadiuchko.springboot.repository.OrderRepository;
import dmytro.hadiuchko.springboot.service.OrderService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemsRepository orderItemsRepository;
    private final OrderItemsMapper orderItemsMapper;
    private final ShoppingCartServiceImpl shoppingCartService;

    @Override
    @Transactional
    public OrderResponseDto createOrder(CreateOrderRequestDto requestDto, Long userId) {
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartForUser(userId);
        Order order = createOrder(shoppingCart, requestDto);
        Order savedOrder = orderRepository.save(order);
        Set<OrderItem> orderItems = createOrderItems(savedOrder, shoppingCart);
        savedOrder.setOrderItems(orderItems);
        return orderMapper.toDto(savedOrder);
    }

    private Order createOrder(ShoppingCart shoppingCart, CreateOrderRequestDto requestDto) {
        Order order = new Order();
        order.setUser(shoppingCart.getUser());
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(requestDto.shippingAddress());
        order.setStatus(Status.PENDING);
        order.setPrice(countPrice(shoppingCart.getCartItems()));
        return order;
    }

    @Override
    public OrderResponseDto updateOrder(Long userId,
                                        Long orderId,
                                        UpdateOrderStatusRequestDto requestDto) {
        Order order = findByIdAndUserId(orderId, userId);
        order.setStatus(requestDto.status());
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderResponseDto> getAllOrdersForUserById(Long userId) {
        return orderRepository.findOrdersByUserId(userId).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public List<OrderItemResponseDto> getOrderItemsByOrdersId(Long orderId, Long userId) {
        Order order = findByIdAndUserId(orderId, userId);
        List<OrderItem> orderItems = orderItemsRepository.findByOrderId(order.getId());
        return orderItems.stream()
                .map(orderItemsMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemResponseDto getOrderItemById(Long orderItemId, Long userId, Long orderId) {
        OrderItem orderItem = orderItemsRepository
                .findByIdAndUserIdAndOrderId(orderItemId, userId, orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find order item by id: " + orderItemId));
        return orderItemsMapper.toDto(orderItem);
    }

    private BigDecimal countPrice(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(item -> item.getBook().getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Set<OrderItem> createOrderItems(Order order, ShoppingCart shoppingCart) {
        Set<OrderItem> orderItems = shoppingCart.getCartItems().stream()
                .map(item -> createOrderItem(item, order))
                .collect(Collectors.toSet());
        orderItemsRepository.saveAll(orderItems);
        return orderItems;
    }

    private OrderItem createOrderItem(CartItem item, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setBook(item.getBook());
        orderItem.setQuantity(item.getQuantity());
        orderItem.setPrice(item.getBook().getPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity())));
        return orderItem;
    }

    private Order findByIdAndUserId(Long orderId, Long userId) {
        return orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Can't find order by order id: "
                                + "%s and by user id: %s ", orderId, userId)));
    }
}
