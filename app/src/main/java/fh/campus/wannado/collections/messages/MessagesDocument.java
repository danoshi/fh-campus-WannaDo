package fh.campus.wannado.collections.messages;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessagesDocument {
    private List<Message> messages;
}
