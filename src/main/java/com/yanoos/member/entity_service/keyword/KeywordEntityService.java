package com.yanoos.member.entity_service.keyword;

import com.yanoos.member.entity.Keyword;
import com.yanoos.member.repository.keyword.KeywordRepository;
import com.yanoos.member.business_service.dto.CreateKeywordIn;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class KeywordEntityService {
    private final KeywordRepository keywordRepository;

    @Transactional
    public Keyword createKeyword(CreateKeywordIn createKeywordin) {
        Optional<Keyword> optionalKeyword = keywordRepository.findByKeyword(createKeywordin.getKeyword());


        Keyword keyword;
        keyword = optionalKeyword.orElseGet(() -> keywordRepository.save(Keyword.builder()
                .keyword(createKeywordin.getKeyword())
                .build()));

        return keyword;
    }

    @Transactional
    public List<Keyword> getKeywordsByMemberId(Long memberId) {
        return keywordRepository.findKeywordsByMemberId(memberId);
    }
}
