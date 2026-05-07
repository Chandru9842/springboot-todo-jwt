package Dev.techtide.HelloWorld.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@Data
public class Todo {

    @Id
    @GeneratedValue
    Long id;

    @NotBlank(message = "Title is required")
    String title;

    String description;   // optional now

    Boolean isCompleted;
}