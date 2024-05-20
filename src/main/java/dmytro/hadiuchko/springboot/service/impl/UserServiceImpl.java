package dmytro.hadiuchko.springboot.service.impl;

import dmytro.hadiuchko.springboot.dto.user.request.UserRegistrationRequestDto;
import dmytro.hadiuchko.springboot.dto.user.response.UserResponseDto;
import dmytro.hadiuchko.springboot.entity.User;
import dmytro.hadiuchko.springboot.enums.RoleName;
import dmytro.hadiuchko.springboot.exception.RegistrationException;
import dmytro.hadiuchko.springboot.mapper.UserMapper;
import dmytro.hadiuchko.springboot.repository.RoleRepository;
import dmytro.hadiuchko.springboot.repository.ShoppingCartRepository;
import dmytro.hadiuchko.springboot.repository.UserRepository;
import dmytro.hadiuchko.springboot.service.ShoppingCartService;
import dmytro.hadiuchko.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartService shoppingCartService;

    @Override
    @Transactional
    public UserResponseDto register(UserRegistrationRequestDto requestDto) {
        if (repository.findAllByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("User with email: " + requestDto.getEmail()
                    + " does already exist");
        }
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findAllByName(RoleName.ROLE_USER));
        User savedUser = repository.save(user);
        shoppingCartService.createShoppingCartForUser(savedUser);
        return userMapper.toUserResponse(savedUser);
    }
}
