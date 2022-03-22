package toyproject.instragram.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import toyproject.instragram.AppConfig;
import toyproject.instragram.entity.Member;
import toyproject.instragram.entity.Post;
import toyproject.instragram.entity.Profile;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(value = AppConfig.class)
@DataJpaTest
class PostRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    PostRepository postRepository;

    @DisplayName("먼저 생성된 날짜 순으로 10개씩 조회")
    @Test
    void getPostsByOrderByCreatedDateDesc() {
        //given
        Profile profile1 = new Profile("nickname1", "이름1", null);
        Member member1 = new Member(null, profile1);
        em.persist(member1);

        Profile profile2 = new Profile("nickname2", "이름2", null);
        Member member2 = new Member(null, profile2);
        em.persist(member2);

        for (int i = 0; i < 100; i++) {
            Member member = i % 2 == 0 ? member1 : member2;
            Post post = new Post(member, null);

            postRepository.save(post);
        }

        //when
        int size = 10;
        PageRequest pageRequest1 = PageRequest.of(0, size);
        Slice<Post> slice1 = postRepository.getPostsByOrderByCreatedDateDesc(pageRequest1);
        PageRequest pageRequest2 = PageRequest.of(10, size);
        Slice<Post> slice2 = postRepository.getPostsByOrderByCreatedDateDesc(pageRequest2);

        //then
        List<Post> posts = slice1.getContent();
        assertThat(slice1.isFirst());
        assertThat(slice2.isLast());
        assertThat(posts.size()).isEqualTo(size);
        assertThat(posts).isSortedAccordingTo((o1, o2) -> o2.getCreatedDate().compareTo(o1.getCreatedDate()));
    }
}