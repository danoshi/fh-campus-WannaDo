package fh.campus.wannado.collections.chats;


import com.google.firebase.Timestamp;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDocument {
    private String chatID;
    private List<String> participantsID;
    private String lastMessage;
    private Timestamp lastMessageTime;


}
