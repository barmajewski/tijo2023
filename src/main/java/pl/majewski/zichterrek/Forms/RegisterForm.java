package pl.majewski.zichterrek.Forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterForm {

    @NotBlank(message = "Pole nie może być puste")
    @NotNull
    @Size(min = 6, message = "Hasło musi zawierać min. 6 znaków")
    private String password;

    @NotBlank(message = "Pole nie może być puste")
    @NotNull
    @Email(message = "Wprowadź poprawny adres email")
    private String email;

    @NotBlank(message = "Pole nie może być puste")
    @NotNull
    private String firstName;

    @NotBlank(message = "Pole nie może być puste")
    @NotNull
    private String lastName;
}
