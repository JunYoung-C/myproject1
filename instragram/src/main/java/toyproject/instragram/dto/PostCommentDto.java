package toyproject.instragram.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class PostCommentDto {
    private Long commentId;
    private String text;

    public PostCommentDto(Long commentId, String text) {
        this.commentId = commentId;
        this.text = text;
    }
}