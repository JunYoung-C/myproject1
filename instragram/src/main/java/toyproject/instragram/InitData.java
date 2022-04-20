package toyproject.instragram;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.comment.entity.Comment;
import toyproject.instragram.member.entity.Member;
import toyproject.instragram.member.entity.MemberImage;
import toyproject.instragram.member.entity.Privacy;
import toyproject.instragram.post.entity.Post;
import toyproject.instragram.post.entity.PostFile;
import toyproject.instragram.reply.entity.Reply;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitData {

    private final InitPostService initPostService;

    @PostConstruct
    public void init() {
        initPostService.init();
    }

    @Component
    @Transactional
    static class InitPostService {

        @PersistenceContext
        EntityManager em;

        public void init() {
            Member member1 = new Member(
                    Privacy.create("1234", "01011111111"),
                    "doforme",
                    "최준영");
            member1.changeProfileImage(new MemberImage("01", "01", "png"));

            Member member2 = new Member(
                    Privacy.create("1234", "test@naver.com"),
                    "chlwnsdud",
                    "김영준");
            member2.changeProfileImage(new MemberImage("02", "02", "png"));

            em.persist(member1);
            em.persist(member2);

            for (int i = 0; i < 20; i++) {
                Post post = new Post(member1, "테스트 게시물" + i);
                post.addPostFile(new PostFile("02", "02", "png"));
                post.addPostFile(new PostFile("03", "03", "png"));
                post.addPostFile(new PostFile("01", "01", "png"));
                em.persist(post);
            }

            Post post1 = new Post(member1, "안녕하세요~");
            post1.addPostFile(new PostFile("02", "01", "png"));
            post1.addPostFile(new PostFile("01", "02", "png"));
            post1.addPostFile(new PostFile("03", "03", "png"));
            em.persist(post1);

            Post post2 = new Post(member2, "반갑습니다~");
            post2.addPostFile(new PostFile("playstore", "playstore", "png"));
            em.persist(post2);

            Comment comment1 = new Comment(post1, member1, "정말 멋있어요!");
            Comment comment2 = new Comment(post1, member2, "여기 어디에요?");
            Comment comment3 = new Comment(post1, member2, "저도 갈래요!");
            Comment comment4 = new Comment(post2, member2, "안녕하세요!");
            Comment comment5 = new Comment(post2, member2, "반가워요!");
            Comment comment6 = new Comment(post2, member2, "좋은 하루에요~");

            for (int i = 0; i < 40; i++) {
                em.persist(new Comment(post2, member2, "테스트 댓글" + i));
            }

            em.persist(comment1);
            em.persist(comment2);
            em.persist(comment3);
            em.persist(comment4);
            em.persist(comment5);
            em.persist(comment6);

            for (int i = 0; i < 40; i++) {
                em.persist(new Reply(comment2, member2, "테스트 답글" + i));
            }
            for (int i = 0; i < 4; i++) {
                em.persist(new Reply(comment1, member1, "테스트 답글" + i));
            }
            Reply reply1 = new Reply(comment2, member1, "여기는 북극입니다!!");
            Reply reply2 = new Reply(comment2, member2, "저런 곳 가보는게 제 버킷리스트에요. 부러워요ㅠㅠ");
            Reply reply3 = new Reply(comment2, member1, "다음에 갈 때 같이 가요!!");
            Reply reply4 = new Reply(comment2, member2, "좋아요!");
            em.persist(reply1);
            em.persist(reply2);
            em.persist(reply3);
            em.persist(reply4);

            em.flush();
            em.clear();
        }
    }
}
