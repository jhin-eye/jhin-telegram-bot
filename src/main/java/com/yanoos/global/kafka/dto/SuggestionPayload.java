package com.yanoos.global.kafka.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SuggestionPayload {
    private Long suggestionId;
    private String type;
    private Long memberId;
    private String title;
    private String content;
    private ZonedDateTime createdAt;
}
