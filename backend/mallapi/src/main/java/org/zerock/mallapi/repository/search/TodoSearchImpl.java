package org.zerock.mallapi.repository.search;

import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.mallapi.domain.QTodo;
import org.zerock.mallapi.domain.Todo;
import org.zerock.mallapi.dto.PageRequestDTO;

import java.util.List;

// 동적 쿼리 생성을 위해 querydsl을 사용하는데, interface만들어주고 그걸 상속받아서 활용하는 impl을 만들어줘야함
@Log4j2
public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch {

    // 생성자
    public TodoSearchImpl() {
        super(Todo.class); // 도메인 class 지정
    }

    @Override
    public Page<Todo> search1(PageRequestDTO pageRequestDTO) {

        log.info("search1........................");

        // 쿼리를 날리기 위한 객체
        QTodo todo = QTodo.todo;

        JPQLQuery<Todo> query = from(todo);

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("tno").descending());

        this.getQuerydsl().applyPagination(pageable, query);

        List<Todo> list = query.fetch(); // 목록 데이터

        long total = query.fetchCount();

        return new PageImpl<>(list, pageable, total);
    }
}
