package com.yanoos.global.kafka.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class KafkaMessageIn {
    @JsonProperty("value")
    private ValFindKeywordPost value;
    @JsonProperty("parentEventId")
    private Long parentEventId;
    @JsonProperty("eventId")
    private Long eventId;
    @JsonProperty("eventType")
    private String eventType;
    @JsonProperty("containKeywords")
    private List<String> containKeywords;
}
