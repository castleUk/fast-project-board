package com.fastcompus.projectboard.repository;

import com.fastcompus.projectboard.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentRepository  extends JpaRepository<ArticleComment, Long> {

}
