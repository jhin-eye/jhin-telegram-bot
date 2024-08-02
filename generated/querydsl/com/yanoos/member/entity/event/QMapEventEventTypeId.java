package com.yanoos.member.entity.event;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMapEventEventTypeId is a Querydsl query type for MapEventEventTypeId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QMapEventEventTypeId extends BeanPath<MapEventEventTypeId> {

    private static final long serialVersionUID = 1071812178L;

    public static final QMapEventEventTypeId mapEventEventTypeId = new QMapEventEventTypeId("mapEventEventTypeId");

    public final NumberPath<Long> eventId = createNumber("eventId", Long.class);

    public final NumberPath<Long> eventTypeId = createNumber("eventTypeId", Long.class);

    public QMapEventEventTypeId(String variable) {
        super(MapEventEventTypeId.class, forVariable(variable));
    }

    public QMapEventEventTypeId(Path<? extends MapEventEventTypeId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMapEventEventTypeId(PathMetadata metadata) {
        super(MapEventEventTypeId.class, metadata);
    }

}

