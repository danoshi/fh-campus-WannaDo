package fh.campus.wannado.collections.chats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private String id;
    private String nickname;
}
