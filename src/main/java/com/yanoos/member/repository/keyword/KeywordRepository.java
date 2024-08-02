package com.yanoos.member.repository.keyword;

import com.yanoos.member.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword,Long>, KeywordRepositoryCustom {
    Optional<Keyword> findByKeyword(String keyword);
}
