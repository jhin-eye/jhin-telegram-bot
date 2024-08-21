package com.yanoos.member.repository.member;

import com.yanoos.member.entity.MapMemberPost;
import com.yanoos.member.entity.Member;
import com.yanoos.member.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapMemberPostEntityRepository extends JpaRepository<MapMemberPost, Long> {
    public MapMemberPost findMapMemberPostByMemberAndPost(Member member, Post post);
}
