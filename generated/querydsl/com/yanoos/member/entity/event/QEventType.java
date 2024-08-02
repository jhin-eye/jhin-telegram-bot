package com.yanoos.member.entity.event;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEventType is a Querydsl query type for EventType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEventType extends EntityPathBase<EventType> {

    private static final long serialVersionUID = -550734635L;

    public static final QEventType eventType1 = new QEventType("eventType1");

    public final StringPath eventType = createString("eventType");

    public final NumberPath<Long> eventTypeId = createNumber("eventTypeId", Long.class);

    public QEventType(String variable) {
        super(EventType.class, forVariable(variable));
    }

    public QEventType(Path<? extends EventType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEventType(PathMetadata metadata) {
        super(EventType.class, metadata);
    }

}

