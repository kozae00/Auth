package com.example.auth.global;

import com.example.auth.domain.member.member.entity.Member;
import com.example.auth.domain.member.member.service.MemberService;
import com.example.auth.domain.post.post.entity.Post;
import com.example.auth.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {

    private final PostService postService;
    private final MemberService memberService;

    @Autowired
    @Lazy
    private BaseInitData self;

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            self.memberInit();
            self.postInit();
        };
    }

    @Transactional
    public void memberInit() {

        if(memberService.count() > 0) {
            return;
        }

        // 회원 샘플데이터 생성
        memberService.join("system", "system1234", "시스템");
        memberService.join("admin", "admin1234", "관리자");
        memberService.join("user1", "user11234", "유저1");
        memberService.join("user2", "user21234", "유저2");
        memberService.join("user3", "user31234", "유저3");

    }

    @Transactional
    public void postInit() {

        if(postService.count() > 0) {
            return;
        }

        Member user1 = memberService.findByUsername("user1").get();
        Member user2 = memberService.findByUsername("user2").get();


        Post post1 = postService.write(user1, "해외축구에서 누가 축구 제일 잘하는 것 같아?", "킹찍히 호날두가 goat인듯");
        post1.addComment(user1, "젖닌이 수듄");
        post1.addComment(user2, "메시가 GOAT이지...");

        Post post2 = postService.write(user1, "배가 고픈데", "피자를 먹을까? 치킨을 먹을까?");
        post2.addComment(user1, "족발 먹으렴.");

        postService.write(user2, "title3", "content3");

    }



}
