package org.zerock.mallapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.mallapi.domain.Todo;
import org.zerock.mallapi.repository.search.TodoSearch;

// JpaRepository<Repository가 사용 할 entity = Todo, 이 entity의 pk값의 타입 = tno니까 Long타입>
// 이렇게 jpa를 상속받으면 자동으로 기본 함수를 사용할 수 있음 ex) findById, get 등..
// 맨 뒤에 TodoSearch는 동적 쿼리를 위한 querydsl을 작성한 클래스임
public interface TodoRepository extends JpaRepository<Todo, Long>, TodoSearch {
}
