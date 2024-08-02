package com.yanoos.member.entity.event;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMapEventEventType is a Querydsl query type for MapEventEventType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMapEventEventType extends EntityPathBase<MapEventEventType> {

    private static final long serialVersionUID = -870392105L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMapEventEventType mapEventEventType = new QMapEventEventType("mapEventEventType");

    public final QEvent event;

    public final QEventType eventType;

    public final QMapEventEventTypeId id;

    public final BooleanPath published = createBoolean("published");

    public QMapEventEventType(String variable) {
        this(MapEventEventType.class, forVariable(variable), INITS);
    }

    public QMapEventEventType(Path<? extends MapEventEventType> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMapEventEventType(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMapEventEventType(PathMetadata metadata, PathInits inits) {
        this(MapEventEventType.class, metadata, inits);
    }

    public QMapEventEventType(Class<? extends MapEventEventType> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.event = inits.isInitialized("event") ? new QEvent(forProperty("event")) : null;
        this.eventType = inits.isInitialized("eventType") ? new QEventType(forProperty("eventType")) : null;
        this.id = inits.isInitialized("id") ? new QMapEventEventTypeId(forProperty("id")) : null;
    }

}

