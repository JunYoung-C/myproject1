package toyproject.instragram.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.member.entity.Member;
import toyproject.instragram.post.entity.Post;
import toyproject.instragram.post.entity.PostFile;
import toyproject.instragram.member.repository.MemberRepository;
import toyproject.instragram.post.repository.PostRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    private final int MAX_POST_SIZE = 10;

    @Transactional
    public Long addPost(PostDto postDto) {
        Member member = memberRepository.findById(postDto.getMemberId()).orElse(null);
        Post post = new Post(member, postDto.getText());
        postDto.getFileDtos().forEach(file -> post.addPostFile(
                new PostFile(post, file.getUploadFileName(), file.getStoreFileName(), file.getExtension())));

        postRepository.save(post);
        return post.getId();
    }

    public Slice<Post> getPostSlice(int page) {
        return postRepository.getPostsByOrderByCreatedDateDesc(PageRequest.of(page, MAX_POST_SIZE));
    }

    public Optional<Post> getOwnerPost(Long postId, Long memberId) {
        Optional<Post> findPost = postRepository.findById(postId);

        if (findPost.isEmpty() || !isMyPost(memberId, findPost)) {
            return Optional.empty();
        }
        return findPost;
    }

    private boolean isMyPost(Long memberId, Optional<Post> findPost) {
        return findPost.get().getMember().getId().equals(memberId);
    }

    @Transactional
    public void modifyPostText(Long postId, String modifiedText) {
        Post post = postRepository.findById(postId).orElse(null);
        //TODO 예외처리
        post.changeText(modifiedText);
    }

    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}

