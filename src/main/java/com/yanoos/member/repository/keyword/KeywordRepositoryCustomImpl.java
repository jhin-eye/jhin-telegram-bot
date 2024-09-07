package com.yanoos.member.repository.keyword;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yanoos.global.entity.member.Keyword;
import lombok.RequiredArgsConstructor;

import java.util.List;
import static com.yanoos.global.entity.member.QKeyword.keyword1;
import static com.yanoos.global.entity.member.QMapMemberKeyword.mapMemberKeyword;


@RequiredArgsConstructor
public class KeywordRepositoryCustomImpl implements KeywordRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Keyword> findKeywordsByMemberId(long memberId) {
        // QMapMemberKeyword mapMemberKeyword = QMapMemberKeyword.mapMemberKeyword;
        // QKeyword keyword = QKeyword.keyword1;

        return queryFactory.selectFrom(keyword1)
                .innerJoin(mapMemberKeyword).on(mapMemberKeyword.keyword.eq(keyword1))
                .where(mapMemberKeyword.member.id.eq(memberId))
                .fetch();
    }
}
