package com.yanoos.member.business_service.member;

import com.yanoos.global.entity.member.Member;
import com.yanoos.global.util.AuthUtil;
import com.yanoos.member.entity_service.map_member_telegram_user.MapMemberTelegramUserEntityService;
import com.yanoos.member.entity_service.member.MemberEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberBusinessService {
    private final MemberEntityService memberEntityService;
    private final MapMemberTelegramUserEntityService mapMemberTelegramUserEntityService;
    private final AuthUtil authUtil;
    @Value("${telegram.authentication-uuid-expiration-time}")
    private int TELEGRAM_UUID_EXPIRED_MILLIS;

    @Transactional
    public Member registerTelegramUserByUuid(Long telegramUserId, UUID telegramAuthenticationUuid) {
        Member member = memberEntityService.getMemberByTelegramAuthenticationUuid(telegramAuthenticationUuid);
        validateRegisterTelegramUserByUuid(member);

        mapMemberTelegramUserEntityService.saveMapMemberTelegramUser(member.getId(),telegramUserId);
        member = member.cleanTelegramUuid();
        return member;

    }

    private void validateRegisterTelegramUserByUuid(Member member) {
        if(!member.getMapMemberTelegramUsers().isEmpty()){
            throw new IllegalArgumentException("Already registered");
        }
        long telegramUuidCreatedAtMillis = member.getTelegramUuidCreatedAt();
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - telegramUuidCreatedAtMillis > TELEGRAM_UUID_EXPIRED_MILLIS) {
            throw new IllegalArgumentException("Expired");
        }
    }
}
