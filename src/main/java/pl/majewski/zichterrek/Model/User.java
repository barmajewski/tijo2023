package pl.majewski.zichterrek.Model;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "uzytkownicy")
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "haslo")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "imie")
    private String firstName;

    @Column(name = "nazwisko")
    private String lastName;

    @Type(type = "list-array")
    @Column(name = "uprawnienia", columnDefinition = "text[]")
    private List<String> authorities;

    @Column(name = "kod_aktywujący")
    private String activationCode;

    @Column(name = "aktywne")
    private Boolean enabled;
}
