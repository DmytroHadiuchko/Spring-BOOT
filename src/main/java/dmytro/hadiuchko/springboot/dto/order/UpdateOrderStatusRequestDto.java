package dmytro.hadiuchko.springboot.dto.order;

import dmytro.hadiuchko.springboot.enums.Status;

public record UpdateOrderStatusRequestDto(Status status) {
}
