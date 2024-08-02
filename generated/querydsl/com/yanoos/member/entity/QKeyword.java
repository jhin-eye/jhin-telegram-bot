package com.yanoos.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QKeyword is a Querydsl query type for Keyword
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKeyword extends EntityPathBase<Keyword> {

    private static final long serialVersionUID = -1544763106L;

    public static final QKeyword keyword1 = new QKeyword("keyword1");

    public final StringPath keyword = createString("keyword");

    public final NumberPath<Long> keywordId = createNumber("keywordId", Long.class);

    public final ListPath<MapMemberKeyword, QMapMemberKeyword> mapMemberKeywords = this.<MapMemberKeyword, QMapMemberKeyword>createList("mapMemberKeywords", MapMemberKeyword.class, QMapMemberKeyword.class, PathInits.DIRECT2);

    public QKeyword(String variable) {
        super(Keyword.class, forVariable(variable));
    }

    public QKeyword(Path<? extends Keyword> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKeyword(PathMetadata metadata) {
        super(Keyword.class, metadata);
    }

}

