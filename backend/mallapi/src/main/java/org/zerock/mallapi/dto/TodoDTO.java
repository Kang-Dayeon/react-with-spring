package org.zerock.mallapi.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {
    // key와 value가 정해져 있지만 받아야할 파라미터가 많은 경우 DTO객체를 사용
    private Long tno;

    private String title;

    private String content;

    private boolean complete;

    private LocalDate dueDate;

}
