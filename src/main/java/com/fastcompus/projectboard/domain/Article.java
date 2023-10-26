package com.fastcompus.projectboard.domain;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@ToString
@Table(indexes = {
    @Index(columnList = "title"),
    @Index(columnList = "hashtag"),
    @Index(columnList = "createdAt"),
    @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String title; // 제목

    @Setter
    @Column(nullable = false, length = 10000)
    private String content; // 본문

    @Setter
    private String hashtag; // 해시태그

    //양방향 맵핑 필드
    //List,Set, Map 용도에 따라서 사용한다.
    //이 article에 연동되어 있는 comments는 중복을 허용하지 않고 모아서 보겠다. 라는 뜻
    //mappedBy 로 어디서 온 건지 명시하지 않으면, 두 엔티티 이름을 합쳐버린다. 주의
    //실무에서는 양방향 바인딩을 안하는 경우도 많음. cascading 결합도가 너무 높기 때문
    //foreign key를 안거는 경우도 많다. 팀마다 정책에 따라서
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @ToString.Exclude //circular referencing 이슈
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();


    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt; //생성일시

    @CreatedBy
    @Column(nullable = false, length = 100)
    private String createdBy; // 생성자

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt; //수정일시

    @LastModifiedBy
    @Column(nullable = false, length = 100)
    private String modifiedBy; //수정자

    protected Article() {
    } //모든 JPA Entity는 기본생성자를 가지고 있어야함

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Article article)) {
            return false;
        }
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
