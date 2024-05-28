package org.zerock.mallapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

// db에 있는 칼럼 정보를 객체로 정의하여 사용
@Entity // 실제 db와 맵핑되는 클래스, 데이터 전달에 사용되면 안된다
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "tbl_todo")
public class Todo {
    @Id // pk값 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // db가 자동으로 id값 생성해줌
    private Long tno;

    @Column(nullable = false)
    private String title;

    private String content;

    private boolean complete;

    private LocalDate dueDate;

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeComplete(boolean complete) {
        this.complete = complete;
    }

    public void changeDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
