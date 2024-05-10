package dmytro.hadiuchko.springboot.security;

import dmytro.hadiuchko.springboot.dto.user.request.UserLoginRequestDto;
import dmytro.hadiuchko.springboot.dto.user.response.UserLoginResponseDto;

public interface AuthenticationService {
    public UserLoginResponseDto authenticate(UserLoginRequestDto requestDto);
}
