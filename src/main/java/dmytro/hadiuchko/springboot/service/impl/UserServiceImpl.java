package dmytro.hadiuchko.springboot.service.impl;

import dmytro.hadiuchko.springboot.dto.user.request.UserRegistrationRequestDto;
import dmytro.hadiuchko.springboot.dto.user.response.UserResponseDto;
import dmytro.hadiuchko.springboot.entity.User;
import dmytro.hadiuchko.springboot.exception.RegistrationException;
import dmytro.hadiuchko.springboot.mapper.UserMapper;
import dmytro.hadiuchko.springboot.repository.UserRepository;
import dmytro.hadiuchko.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository repository;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto) {
        if (repository.findAllByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("User with email: " + requestDto.getEmail()
                    + " does already exist");
        }
        User user = userMapper.toModel(requestDto);
        return userMapper.toUserResponse(repository.save(user));
    }
}
