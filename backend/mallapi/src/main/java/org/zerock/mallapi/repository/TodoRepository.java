package org.zerock.mallapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.mallapi.domain.Todo;
import org.zerock.mallapi.repository.search.TodoSearch;

// JpaRepository<Repository가 사용 할 entity, 이 entity의 pk값>
public interface TodoRepository extends JpaRepository<Todo, Long>, TodoSearch {
}
