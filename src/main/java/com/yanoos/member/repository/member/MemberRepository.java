package com.yanoos.member.repository.member;

import com.yanoos.global.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByTelegramAuthenticationUuid(UUID uuid);
}
