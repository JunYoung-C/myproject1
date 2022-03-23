package toyproject.instragram.repository.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostDto {

    @JsonIgnore
    private Long id;
    private PostMemberDto memberDto;
    private String photoPath;
    private LocalDateTime createdDate;
    private List<PostCommentDto> commentDtoList;

    @QueryProjection
    public PostDto(Long id, PostMemberDto memberDto, String photoPath, LocalDateTime createdDate) {
        this.id = id;
        this.memberDto = memberDto;
        this.photoPath = photoPath;
        this.createdDate = createdDate;
    }
}
