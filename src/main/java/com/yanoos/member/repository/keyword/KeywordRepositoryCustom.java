package com.yanoos.member.repository.keyword;


import com.yanoos.global.entity.member.Keyword;

import java.util.List;

public interface KeywordRepositoryCustom {
    List<Keyword> findKeywordsByMemberId(long memberId);
}
