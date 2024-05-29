package org.zerock.mallapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.mallapi.domain.Todo;
import org.zerock.mallapi.repository.search.TodoSearch;

// JpaRepository<Repository가 사용 할 entity, 이 entity의 pk값>
// 이렇게 jpa를 상속받으면 자동으로 기본 함수를 사용할 수 있음 ex) findById, get 등..
public interface TodoRepository extends JpaRepository<Todo, Long>, TodoSearch {
}
