package toyproject.instragram.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    private String text;

    @OneToMany(mappedBy = "comment")
    List<Reply> replies = new ArrayList<>();

    public void changePost(Post post) {
        this.post = post;
    }

    // 연관관계 편의 메소드
    public void addReply(Reply reply) {
        replies.add(reply);
        reply.changeComment(this);
    }
}
