package pl.majewski.zichterrek.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "komentarze")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "komentarz_id")
    private Long commentId;

    @Column(name = "tresc")
    private String content;

//    @Column(name = "data_dodania")
//    @DateTimeFormat(pattern = "dd-MM-yyyy")
//    private LocalDate dateTimeAdded;
}
