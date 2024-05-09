package tasc.finalproject.AccountService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity {

    private LocalDateTime created_at;

    private String created_by;

    private LocalDateTime updated_at;

    private String updated_by;
}
