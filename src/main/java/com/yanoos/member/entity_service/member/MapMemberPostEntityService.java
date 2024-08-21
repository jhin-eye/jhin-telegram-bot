package com.yanoos.member.entity_service.member;

import com.yanoos.member.entity.MapMemberPost;
import com.yanoos.member.entity.Member;
import com.yanoos.member.entity.Post;
import com.yanoos.member.repository.member.MapMemberPostEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor
public class MapMemberPostEntityService {
    private final MapMemberPostEntityRepository mapMemberPostEntityRepository;;

    public MapMemberPost getMapMemberPostByMemberIdAndPostId(Member member, Post post) {
        return mapMemberPostEntityRepository.findMapMemberPostByMemberAndPost(member, post);
    }
}
