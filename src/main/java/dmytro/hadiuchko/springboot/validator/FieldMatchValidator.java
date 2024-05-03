package dmytro.hadiuchko.springboot.validator;

import dmytro.hadiuchko.springboot.dto.user.request.UserRegistrationRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch,
        UserRegistrationRequestDto> {

    @Override
    public boolean isValid(UserRegistrationRequestDto requestDto,
                           ConstraintValidatorContext constraintValidatorContext) {

        return Objects.equals(requestDto.getPassword(), requestDto.getRepeatPassword());
    }
}
