package com.yanoos.member.entity_service.board;

import com.yanoos.global.entity.board.Board;
import com.yanoos.member.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardEntityService {
    private final BoardRepository boardRepository;

    public Board getBoardByBoardId(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow();
    }
}
