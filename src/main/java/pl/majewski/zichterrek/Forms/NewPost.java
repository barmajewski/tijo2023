package pl.majewski.zichterrek.Forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.majewski.zichterrek.Model.Tag;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewPost {
    private String message;
    private Set<Tag> tags;

}
