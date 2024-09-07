package com.yanoos.member.entity_service.member;

import com.yanoos.global.entity.board.Post;
import com.yanoos.global.entity.member.MapMemberPost;
import com.yanoos.global.entity.member.Member;
import com.yanoos.member.repository.member.MapMemberPostEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapMemberPostEntityService {
    private final MapMemberPostEntityRepository mapMemberPostEntityRepository;;

    public MapMemberPost getMapMemberPostByMemberIdAndPostId(Member member, Post post) {
        return mapMemberPostEntityRepository.findMapMemberPostByMemberAndPost(member, post);
    }
}
