package pl.majewski.zichterrek.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "wiadomosc")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wiadomosc_id")
    private Long messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uzytkownik_id")
    @JsonIgnore
    private User user;

    @Column(name = "tresc_widomosci", columnDefinition = "TEXT")
    private String messageContent;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "wiadomosc_tagi",
            joinColumns = @JoinColumn(table = "wiadomosc", name = "wiadomosc_id"),
            inverseJoinColumns = @JoinColumn(table = "tag", name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @JoinColumn(name = "komentarze")
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comments> commentsList;

//    @Column(name = "data_dodania")
//    @DateTimeFormat(pattern = "dd-MM-yyyy")
//    private LocalDate dateTimeAdded;

    @JoinColumn(name = "zdjęcie")
    @OneToOne
    private File attachment;

    @Transient
    @JsonIgnore
    private String[] tagNames;
}
