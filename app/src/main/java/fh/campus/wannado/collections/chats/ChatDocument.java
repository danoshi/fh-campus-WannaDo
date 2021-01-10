package fh.campus.wannado.collections.chats;


import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatDocument {
    private List<String> participantsID;
    private String lastMessage;
    private LocalDateTime lastMessageTime;
}
