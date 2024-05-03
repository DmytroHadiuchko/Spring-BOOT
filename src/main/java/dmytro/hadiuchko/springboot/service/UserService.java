package dmytro.hadiuchko.springboot.service;

import dmytro.hadiuchko.springboot.dto.user.request.UserRegistrationRequestDto;
import dmytro.hadiuchko.springboot.dto.user.response.UserResponseDto;

public interface UserService {
    public UserResponseDto register(UserRegistrationRequestDto requestDto);
}
