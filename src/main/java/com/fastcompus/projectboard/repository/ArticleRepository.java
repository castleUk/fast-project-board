package com.fastcompus.projectboard.repository;

import com.fastcompus.projectboard.domain.Article;
import com.fastcompus.projectboard.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends
    JpaRepository<Article, Long>,
    QuerydslPredicateExecutor<Article>,
    // QuerydslPredicateExecutor 는기본적으로 엔티티 안에 있는 모든 필드에 대한 기본 검색 기능을 추가해준다
    QuerydslBinderCustomizer<QArticle>
    //QuerydslBinderCustomizer는 추가적으로 우리 입맛에 맞는 검색 기능을 구현하기 위해서 추가함
{

    //검색 기능을 입맛에 맞게 바꾸기 위해서 customize 메소드를 오버라이드해서 재구성
    //interface라 원래는 구현을 넣을수는 없지만, Java8 이후로는 가능
    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        bindings.excludeUnlistedProperties(true); //리스팅 하지 않은 프로퍼티는 검색에서 제외시키는 것을 true로 설정
        bindings.including(root.title,  root.content, root.hashtag, root.createdAt, root.createdBy); //여기서 원하는 필드를 추가한다.
        //bindings.bind(root.title).first(StringExpression::likeIgnoreCase); // like '${value} '
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // like '%${value}%'
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase); // like '%${value}%'
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase); // like '%${value}%'
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase); // like '%${value}%'

    }
}
