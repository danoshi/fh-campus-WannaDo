package fh.campus.wannado.collections.messages;

import java.time.LocalDateTime;

import fh.campus.wannado.collections.chats.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private UserInfo sender;
    private LocalDateTime timestamp;
    private String content;
}
