package com.yanoos.telegram_bot.controller;


import com.yanoos.global.entity.board.Post;
import com.yanoos.global.entity.member.MapMemberPost;
import com.yanoos.global.entity.member.Member;
import com.yanoos.global.kafka.KafkaConsumer;
import com.yanoos.member.entity_service.member.MapMemberPostEntityService;
import com.yanoos.member.entity_service.member.MemberEntityService;
import com.yanoos.member.entity_service.post.PostEntityService;
import com.yanoos.telegram_bot.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Controller
@RequiredArgsConstructor
public class TelegramController {
    private final Bot bot;
    private final PostEntityService postEntityService;
    private final MemberEntityService memberEntityService;
    private final MapMemberPostEntityService mapMemberPostEntityService;
    private final KafkaConsumer kafkaConsumer;

    @GetMapping("/{postId}/{memberId}")
    @ResponseBody
    public String temp(
            @PathVariable("postId") Long postId,
            @PathVariable("memberId") Long memberId
    ) throws TelegramApiException {
        Post post = postEntityService.getPostByPostId(postId);
        Member member = memberEntityService.getMemberByMemberId(memberId);
        MapMemberPost mapMemberPost = mapMemberPostEntityService.getMapMemberPostByMemberIdAndPostId(member, post);
        String parsedMessage = kafkaConsumer.parseMessage(post,mapMemberPost);
        bot.sendText(member.getMapMemberTelegramUsers().get(0).getTelegramUserId(),parsedMessage);

        return "sdf";
    }
}
