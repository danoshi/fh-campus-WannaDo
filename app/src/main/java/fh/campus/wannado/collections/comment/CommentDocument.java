package fh.campus.wannado.collections.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDocument {
    private String threadID;
    private String comment;
    private String userID;

    @Override
    public String toString() {
        return comment + "\n";
    }
}
