package org.zerock.mallapi.repository.search;

import org.springframework.data.domain.Page;
import org.zerock.mallapi.domain.Todo;
import org.zerock.mallapi.dto.PageRequestDTO;

// todo 검색을 위한 DAO 인터페이스
public interface TodoSearch {
    Page<Todo> search1(PageRequestDTO pageRequestDTO);
}
