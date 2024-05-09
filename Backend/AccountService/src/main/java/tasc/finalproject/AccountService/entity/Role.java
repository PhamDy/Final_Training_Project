package tasc.finalproject.AccountService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity{
    private long roleId;
    private RoleName name;
}


