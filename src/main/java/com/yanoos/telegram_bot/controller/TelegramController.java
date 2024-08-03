package com.yanoos.telegram_bot.controller;


import com.yanoos.telegram_bot.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Controller
@RequiredArgsConstructor
public class TelegramController {
    private final Bot bot;

    @GetMapping("/")
    @ResponseBody
    public String temp() throws TelegramApiException {
        bot.sendText(6539777004L,"temP");

        return "sdf";
    }
}
