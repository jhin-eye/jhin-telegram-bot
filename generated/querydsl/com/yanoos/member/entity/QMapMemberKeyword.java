package com.yanoos.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMapMemberKeyword is a Querydsl query type for MapMemberKeyword
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMapMemberKeyword extends EntityPathBase<MapMemberKeyword> {

    private static final long serialVersionUID = 1868835422L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMapMemberKeyword mapMemberKeyword = new QMapMemberKeyword("mapMemberKeyword");

    public final com.yanoos.member.entity.dto.QMapMemberKeywordId id;

    public final QKeyword keyword;

    public final QMember member;

    public QMapMemberKeyword(String variable) {
        this(MapMemberKeyword.class, forVariable(variable), INITS);
    }

    public QMapMemberKeyword(Path<? extends MapMemberKeyword> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMapMemberKeyword(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMapMemberKeyword(PathMetadata metadata, PathInits inits) {
        this(MapMemberKeyword.class, metadata, inits);
    }

    public QMapMemberKeyword(Class<? extends MapMemberKeyword> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new com.yanoos.member.entity.dto.QMapMemberKeywordId(forProperty("id")) : null;
        this.keyword = inits.isInitialized("keyword") ? new QKeyword(forProperty("keyword")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

