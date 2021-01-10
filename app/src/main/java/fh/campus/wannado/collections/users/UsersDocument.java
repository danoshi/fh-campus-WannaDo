package fh.campus.wannado.collections.users;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UsersDocument implements Serializable {
    private final String username;
    private final String email;
}
