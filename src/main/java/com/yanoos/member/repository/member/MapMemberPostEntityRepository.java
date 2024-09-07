package com.yanoos.member.repository.member;

import com.yanoos.global.entity.board.Post;
import com.yanoos.global.entity.member.MapMemberPost;
import com.yanoos.global.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapMemberPostEntityRepository extends JpaRepository<MapMemberPost, Long> {
    public MapMemberPost findMapMemberPostByMemberAndPost(Member member, Post post);
}
