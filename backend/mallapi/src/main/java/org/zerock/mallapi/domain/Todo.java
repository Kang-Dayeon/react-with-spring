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
@NoArgsConstructor // 기본생성자를 생성해줌
@Table(name = "tbl_todo") // 테이블이름
public class Todo {
    @Id // pk값 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // db가 자동으로 id값 생성해줌
    private Long tno;

    @Column(nullable = false)
    private String title;

    private String content;

    private boolean complete;

    private LocalDate dueDate;

    // db update를 위한 메서드들
    // 외부에서 값을 마음대로 변경할 수 있게 하는 @Setter은 사용을 지양하고 직접 직관적인 수정 메서드를 만들어줌
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
