package com.yanoos.member.repository.post;

import com.yanoos.global.entity.board.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

}
