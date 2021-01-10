package fh.campus.wannado.collections.messages;

import java.io.Serializable;
import java.time.LocalDateTime;

import fh.campus.wannado.collections.chats.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {
    private UserInfo sender;
    private String content;
}
