package fh.campus.wannado.collections.messages;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessagesDocument {
    private String chatID;
    private List<Message> messages;
}
