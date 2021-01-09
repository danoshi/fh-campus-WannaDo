package fh.campus.wannado.collections.post;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDocument implements Serializable {
    private final String username;
    private final String title;
    private final String message;

}
