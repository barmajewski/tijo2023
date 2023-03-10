package pl.majewski.zichterrek.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.majewski.zichterrek.Enum.FileTypeEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pliki")
@Entity
@Data
@Builder
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plik_id")
    private Long fileId;

    @Column(name = "nazwa")
    @NotNull
    private String name;

    @Column(name = "url")
    @NotNull
    private String url;

    @Column(name = "rodzaj_pliku")
    @Enumerated(EnumType.STRING)
    private FileTypeEnum fileTypeEnum;
}
