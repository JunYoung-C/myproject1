package toyproject.instragram.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.comment.entity.Comment;
import toyproject.instragram.comment.repository.CommentRepository;
import toyproject.instragram.common.exception.api.ApiExceptionType;
import toyproject.instragram.member.repository.MemberRepository;
import toyproject.instragram.post.repository.PostRepository;

import static toyproject.instragram.common.exception.api.ApiExceptionType.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Long addComment(CommentDto commentDto) {
        Comment comment = new Comment(
                postRepository.findById(commentDto.getPostId()).orElse(null),
                memberRepository.findById(commentDto.getMemberId()).orElse(null),
                commentDto.getText());
        commentRepository.save(comment);
        return comment.getId();
    }

    public Slice<Comment> getCommentSlice(Long postId, Pageable pageable) {
        return commentRepository
                .getCommentsByPostIdOrderByCreatedDateDesc(postId, pageable);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw NOT_FOUND_COMMENT.getException();
        }

        commentRepository.deleteById(commentId);
    }

    // TODO 테스트 코드 작성
    public Long getCommentCount(Long postId) {
        return commentRepository.countCommentsByPostId(postId);
    }

    // TODO 테스트 코드 작성
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(NOT_FOUND_COMMENT::getException);
    }
}
