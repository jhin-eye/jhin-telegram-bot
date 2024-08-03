package com.yanoos.telegram_bot;

import com.yanoos.member.business_service.member.MemberBusinessService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {
    private final MemberBusinessService memberBusinessService;


    @Value("${telegram.token}")
    private String TELEGRAM_BOT_TOKEN;

    private String botToken;

    @PostConstruct
    public void init() {
        this.botToken = TELEGRAM_BOT_TOKEN;
    }
    @Override
    public String getBotUsername() {
        return "tanoos_000001_bot";
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String text = update.getMessage().getText();
        log.info("onUpdateReceived called text = {}", text);
        if(text.startsWith("||")) {
            try {
                UUID telegramAuthenticationUuid = UUID.fromString(text.split(" ")[1]);
                memberBusinessService.registerTelegramUserByUuid(update.getMessage().getFrom().getId(), telegramAuthenticationUuid);
                sendText(update.getMessage().getFrom().getId(), "등록되었습니다.");
            }catch (Exception e){
                log.error("error",e);
                try {
                    sendText(update.getMessage().getFrom().getId(), "등록에 실패했습니다.");
                } catch (TelegramApiException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        log.info("onUpdateReceived done");
    }

    public void sendText(Long who, String what) throws TelegramApiException {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what)
                .build();
        execute(sm);
    }

}
