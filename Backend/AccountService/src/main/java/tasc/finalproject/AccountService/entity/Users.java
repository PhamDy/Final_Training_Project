package tasc.finalproject.AccountService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users extends BaseEntity{
    private long userId;
    private String email;
    private String password;
    private String otp;
    private LocalDateTime otp_generated_time;
    private boolean enabled = false;
}
