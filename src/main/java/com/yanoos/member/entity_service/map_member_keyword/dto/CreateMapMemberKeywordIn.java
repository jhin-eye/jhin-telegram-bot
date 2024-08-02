package com.yanoos.member.entity_service.map_member_keyword.dto;

import com.yanoos.member.entity.Keyword;
import com.yanoos.member.entity.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateMapMemberKeywordIn {
    private Keyword keyword;
    private Member member;

}
