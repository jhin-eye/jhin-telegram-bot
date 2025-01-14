package com.yanoos.global.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanoos.global.entity.board.Board;
import com.yanoos.global.entity.board.Post;
import com.yanoos.global.entity.member.MapMemberPost;
import com.yanoos.global.entity.member.Member;
import com.yanoos.global.kafka.dto.KafkaMessageIn;
import com.yanoos.member.entity_service.board.BoardEntityService;
import com.yanoos.member.entity_service.member.MapMemberPostEntityService;
import com.yanoos.member.entity_service.member.MemberEntityService;
import com.yanoos.member.entity_service.post.PostEntityService;
import com.yanoos.telegram_bot.Bot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KafkaConsumer {
    private final Bot bot;
    private final MemberEntityService memberEntityService;
    private final PostEntityService postEntityService;
    private final MapMemberPostEntityService mapMemberPostEntityService;
    private final BoardEntityService boardEntityService;

    @KafkaListener(topics = "FIND_KEYWORD_POST", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeFindKeywordPost(String message) throws JsonProcessingException, TelegramApiException {
        log.info("Consumed message: {}",message);
        //1. 메시지에 맞는 DTO 형식 작성 -> {"val":"{\"post_id\": 576, \"board_name_eng\": \"geumriver_notice\", \"board_name_kor\": \"금강유역환경청-공지사항\", \"post_no\": \"1562\", \"post_title\": \"2024년 금강사랑 그림 그리기 대회 수상자 알림\", \"post_url\": \"https://www.me.go.kr/gg/web/index.do?menuId=2284\", \"post_write_date\": \"2024-05-20\"}","eventId":69,"eventTypeId":1}
        //2. 메시지를 DTO로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        KafkaMessageIn kafkaMessageIn = objectMapper.readValue(message, KafkaMessageIn.class);
        //3. 변환된 DTO를 이용해 메시지 전송
        log.info("newPostIn: {}", kafkaMessageIn.toString());
        Post post = postEntityService.getPostByPostId(kafkaMessageIn.getValue().getPostId());
        Member member = memberEntityService.getMemberByMemberId(kafkaMessageIn.getValue().getMemberId());
        MapMemberPost mapMemberPost = mapMemberPostEntityService.getMapMemberPostByMemberIdAndPostId(member, post);
        String parsedMessage = parseMessage(post,mapMemberPost);
        bot.sendText(member.getMapMemberTelegramUsers().get(0).getTelegramUserId(),parsedMessage);
    }

    private String parseMessage(Post post,MapMemberPost mapMemberPost) {
        //epoch time to date UTC+9

        return "새로운 게시글이 등록되었습니다.\n" +
                "게시판: " + post.getBoard().getNameKor() + "\n" +
                "게시글 제목: " + post.getTitle() + "\n" +
                "게시글 작성일: " + post.getWriteDate() + "\n" +
                "게시판 URL: " + post.getBoard().getSiteUrl()+ "\n"+
                "포함 키워드: " + mapMemberPost.getKeywords();
    }

    @KafkaListener(topics = "FAIL_CRAWLING", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeFailCrawling(String message) throws JsonProcessingException, TelegramApiException {
        log.info("Consumed message: {}",message);
        //1. 메시지에 맞는 DTO 형식 작성 -> {"val":"{\"post_id\": 576, \"board_name_eng\": \"geumriver_notice\", \"board_name_kor\": \"금강유역환경청-공지사항\", \"post_no\": \"1562\", \"post_title\": \"2024년 금강사랑 그림 그리기 대회 수상자 알림\", \"post_url\": \"https://www.me.go.kr/gg/web/index.do?menuId=2284\", \"post_write_date\": \"2024-05-20\"}","eventId":69,"eventTypeId":1}
        //2. 메시지를 DTO로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        KafkaMessageIn kafkaMessageIn = objectMapper.readValue(message, KafkaMessageIn.class);
        //3. 변환된 DTO를 이용해 메시지 전송
        log.info("failCrawling: {}", kafkaMessageIn.toString());
        Board board = boardEntityService.getBoardByBoardId(kafkaMessageIn.getValue().getBoardId());
        String parsedMessage = parseMessage(board);
        List<Member> members = memberEntityService.getMembersAll();
        for(Member member : members){
            if(member.getMapMemberTelegramUsers().isEmpty()){
                continue;
            }
            bot.sendText(member.getMapMemberTelegramUsers().get(0).getTelegramUserId(),parsedMessage);
        }

    }

    @KafkaListener(topics = "CRAWLING_STATUS", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeCrawlingStatus(String message) throws JsonProcessingException, TelegramApiException {
        log.info("Consumed message: {}",message);
        //1. 메시지에 맞는 DTO 형식 작성 -> {"val":"{\"post_id\": 576, \"board_name_eng\": \"geumriver_notice\", \"board_name_kor\": \"금강유역환경청-공지사항\", \"post_no\": \"1562\", \"post_title\": \"2024년 금강사랑 그림 그리기 대회 수상자 알림\", \"post_url\": \"https://www.me.go.kr/gg/web/index.do?menuId=2284\", \"post_write_date\": \"2024-05-20\"}","eventId":69,"eventTypeId":1}
        //2. 메시지를 DTO로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        KafkaMessageIn kafkaMessageIn = objectMapper.readValue(message, KafkaMessageIn.class);
        //3. 변환된 DTO를 이용해 메시지 전송
        log.info("crawlingStatus: {}", kafkaMessageIn.toString());
        String parsedMessage = parseMessage(kafkaMessageIn.getValue().getCrawlingStatus());
        List<Member> members = memberEntityService.getMembersAll();
        for(Member member : members){
            if(member.getMapMemberTelegramUsers().isEmpty()){
                continue;
            }
            bot.sendText(member.getMapMemberTelegramUsers().get(0).getTelegramUserId(),parsedMessage);
        }

    }

    private String parseMessage(Board board) {
        //epoch time to date UTC+9

        return "게시판 조회 실패\n" +
                "게시판명 : " + board.getNameKor();
    }

    private String parseMessage(String crawlingStatus) {
        //epoch time to date UTC+9
        if(crawlingStatus.equalsIgnoreCase("START")){
            return "크롤링 작업을 시작합니다";
        } else if (crawlingStatus.equalsIgnoreCase("END")) {
            return "크롤링 작업이 완료되었습니다";
        }
        return "크롤링작업에러";
    }
}
