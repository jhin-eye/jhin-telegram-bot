package com.yanoos.member.entity_service.map_member_telegram_user;

import com.yanoos.member.entity.MapMemberTelegramUser;
import com.yanoos.member.entity.Member;
import com.yanoos.member.entity_service.member.MemberEntityService;
import com.yanoos.member.repository.map_member_telegram_user.MapMemberTelegramUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MapMemberTelegramUserEntityService {
    private final MapMemberTelegramUserRepository mapMemberTelegramUserRepository;
    private final MemberEntityService memberEntityService;

    public MapMemberTelegramUser saveMapMemberTelegramUser(Long memberId, Long telegramUserId) {
        Member member = memberEntityService.getMemberByMemberId(memberId);
        return mapMemberTelegramUserRepository.save(MapMemberTelegramUser.builder()
                .telegramUserId(telegramUserId)
                .member(member)
                .build());
    }
}
