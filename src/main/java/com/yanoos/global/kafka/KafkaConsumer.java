package com.yanoos.global.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanoos.global.entity.board.Board;
import com.yanoos.global.entity.board.Post;
import com.yanoos.global.entity.member.MapMemberPost;
import com.yanoos.global.entity.member.Member;
import com.yanoos.global.kafka.dto.KafkaMessageIn;
import com.yanoos.global.kafka.dto.SuggestionPayload;
import com.yanoos.member.entity_service.board.BoardEntityService;
import com.yanoos.member.entity_service.member.MapMemberPostEntityService;
import com.yanoos.member.entity_service.member.MemberEntityService;
import com.yanoos.member.entity_service.post.PostEntityService;
import com.yanoos.telegram_bot.Bot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.format.DateTimeFormatter;
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
    private final ObjectMapper objectMapper;
    @Value("${server.url}")
    private String SERVER_URL;

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

    public String parseMessage(Post post, MapMemberPost mapMemberPost) {
        //epoch time to date UTC+9

        return "새로운 게시글이 등록되었습니다.\n" +
                "게시판: " + post.getBoard().getNameKor() + "\n" +
                "게시글 제목: " + post.getTitle() + "\n" +
                "게시글 작성일: " + post.getWriteDate() + "\n" +
                "게시글 URL: " + String.format("%s/api/view/posts/%d/site",SERVER_URL,post.getId())+ "\n"+
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH시 mm분");
        String formattedDate = board.getLastCrawledAt().format(formatter);

        return "게시판 조회 실패\n" +
                "게시판명 : " + board.getNameKor() +
                "\n 마지막 조회 성공: " + formattedDate;
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

    @KafkaListener(
            topics = "NEW_SUGGESTION",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "suggestionKafkaListenerContainerFactory"
    )
    public void consumeSuggestionAlert(ConsumerRecord<String, String> record) throws JsonProcessingException, TelegramApiException {
        String message = record.value();
        log.info("Consumed suggestion message: {}", message);

        SuggestionPayload suggestion = objectMapper.readValue(message, SuggestionPayload.class);

        // 텔레그램 전송 대상 설정 (예: 관리자 계정 or 운영자 그룹)
        List<Member> members = memberEntityService.getAdminMembers();
        if (members.isEmpty()) {
            log.warn("❗관리자 목록이 비어 있어 메시지를 처리하지 못했습니다.");
            return;
        }
        log.info("current partition: {}", record.partition());
        log.info("currentConsumeAdmin: {}", members.get(record.partition()%members.size()).getId());
        Member currentConsumeAdmin = members.get(record.partition()%members.size());
        String parsedMessage = parseSuggestionMessage(suggestion);
        try {
            bot.sendText(currentConsumeAdmin.getMapMemberTelegramUsers().get(0).getTelegramUserId(), parsedMessage);
        } catch (TelegramApiException e) {
            log.error("Failed to send suggestion message to member: {}", currentConsumeAdmin.getId(), e);
        }
    }

    private String parseSuggestionMessage(SuggestionPayload payload) {
        return "\uD83D\uDCDD 새로운 건의사항이 등록되었습니다\n\n" +
                "🗂️건의사항 ID: " + payload.getSuggestionId() + "\n" +
                "🧑‍💻 사용자 ID: " + payload.getMemberId() + "\n" +
                "📌 제목: " + payload.getTitle() + "\n" +
                "💬 내용: " + payload.getContent() + "\n" +
                "🕐 등록 시간: " + payload.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "\n";
    }


}
