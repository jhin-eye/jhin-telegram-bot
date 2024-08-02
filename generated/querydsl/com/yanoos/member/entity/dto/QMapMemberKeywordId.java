package com.yanoos.member.entity.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMapMemberKeywordId is a Querydsl query type for MapMemberKeywordId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QMapMemberKeywordId extends BeanPath<MapMemberKeywordId> {

    private static final long serialVersionUID = 796720904L;

    public static final QMapMemberKeywordId mapMemberKeywordId = new QMapMemberKeywordId("mapMemberKeywordId");

    public final NumberPath<Long> keywordId = createNumber("keywordId", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public QMapMemberKeywordId(String variable) {
        super(MapMemberKeywordId.class, forVariable(variable));
    }

    public QMapMemberKeywordId(Path<? extends MapMemberKeywordId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMapMemberKeywordId(PathMetadata metadata) {
        super(MapMemberKeywordId.class, metadata);
    }

}

