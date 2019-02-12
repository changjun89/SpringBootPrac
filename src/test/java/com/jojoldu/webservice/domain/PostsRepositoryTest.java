package com.jojoldu.webservice.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jojoldu.webservice.domain.posts.Posts;
import com.jojoldu.webservice.domain.posts.PostsRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

	
    @Autowired
    private TestEntityManager testEntityManager;
    
    
    @Autowired
    PostsRepository postsRepository;
    
    Posts post = null;
    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }
    

    @Test
    public void 게시글저장_불러오기() {
    }

    @Test
    public void BaseTimeEntity_등록 () {
        //given
        LocalDateTime now = LocalDateTime.now();
        postsRepository.save(Posts.builder()
                .title("테스트 게시글")
                .content("테스트 본문")
                .author("jojoldu@gmail.com")
                .build());
        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertTrue(posts.getCreatedDate().isAfter(now));
        assertTrue(posts.getModifiedDate().isAfter(now));
    }
    
    @Test
    public void 데이터등록후_삭제 () {
    	Posts post = Posts.builder().author("삭제 테스트 ").title("삭제").content("content").build();
        testEntityManager.persist(post);
        assertThat(post.getTitle().equals("삭제"));
        
        postsRepository.delete(post.getId());
        
    }
}