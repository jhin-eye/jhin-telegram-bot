package com.yanoos.global.kafka;

import com.yanoos.telegram_bot.Bot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final Bot bot;
    @KafkaListener(topics = "NEW_POST", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message){
        log.info("Consumed message: {}",message);
        //1. 메시지에 맞는 DTO 형식 작성 -> {"val":"{\"post_id\": 576, \"board_name_eng\": \"geumriver_notice\", \"board_name_kor\": \"금강유역환경청-공지사항\", \"post_no\": \"1562\", \"post_title\": \"2024년 금강사랑 그림 그리기 대회 수상자 알림\", \"post_url\": \"https://www.me.go.kr/gg/web/index.do?menuId=2284\", \"post_write_date\": \"2024-05-20\"}","eventId":69,"eventTypeId":1}

        //2. 메시지를 DTO로 변환
        //3. 변환된 DTO를 이용해 메시지 전송
    }
}
