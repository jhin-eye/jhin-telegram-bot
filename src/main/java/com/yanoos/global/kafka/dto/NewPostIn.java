package com.yanoos.global.kafka.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class NewPostIn {
    @JsonProperty("value")
    private ValNewPostIn value;
    @JsonProperty("eventId")
    private Long eventId;
    @JsonProperty("eventType")
    private String eventType;
}
