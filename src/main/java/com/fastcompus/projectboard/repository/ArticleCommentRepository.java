package com.fastcompus.projectboard.repository;

import com.fastcompus.projectboard.domain.ArticleComment;
import com.fastcompus.projectboard.domain.QArticleComment;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleCommentRepository  extends JpaRepository<ArticleComment, Long>,
    QuerydslPredicateExecutor<ArticleComment>,
    QuerydslBinderCustomizer<QArticleComment>
{

    @Override
    default void customize(QuerydslBindings bindings, QArticleComment root) {
        bindings.excludeUnlistedProperties(true); //리스팅 하지 않은 프로퍼티는 검색에서 제외시키는 것을 true로 설정
        bindings.including(root.content, root.createdAt, root.createdBy); //여기서 원하는 필드를 추가한다.
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase); // like '%${value}%'
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase); // like '%${value}%'

    }
}
