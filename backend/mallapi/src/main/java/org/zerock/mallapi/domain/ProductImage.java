package org.zerock.mallapi.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

// product image 객체를 하나로 묶어서 사용하기 위한 어노테이션
@Embeddable
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {
    private  String fileName;

    @Setter
    private int ord; // 순번

}
