package fh.campus.wannado.collections.chats;


import com.google.firebase.Timestamp;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private long lastMessageTime;

    public static String getTimeDate(long lastMessageTime){
        try{
            DateFormat dateFormat = DateFormat.getDateTimeInstance();
            Date newDate = (new Date(lastMessageTime));
            return dateFormat.format(newDate);
        }
        catch (Exception e){
            return "date";
        }
    }




}
