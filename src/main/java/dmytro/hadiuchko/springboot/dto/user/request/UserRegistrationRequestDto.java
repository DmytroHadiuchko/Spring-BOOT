package dmytro.hadiuchko.springboot.dto.user.request;

import dmytro.hadiuchko.springboot.validator.FieldMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword",
                message = "The password fields must match")
})
@Data
public class UserRegistrationRequestDto {
    @NotBlank(message = "Email shouldn't be empty")
    @Size(min = 4, message = "Email should contain min 4 characters")
    private String email;
    @NotBlank(message = "Password shouldn't be empty")
    @Size(min = 4, message = "Password should contain min 4 characters")
    private String password;
    private String repeatPassword;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String shippingAddress;
}
