package org.zerock.mallapi.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.mallapi.domain.Todo;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class TodoRepositoryTests {

    @Autowired // 의존성 주입
    private TodoRepository todoRepository;
    // TodoRepository는 jpa를 상속받았기 때문에 자동으로 만들어진 함수들을 사용할 수 있다

    @Test
    public void test1() {
        Assertions.assertNotNull(todoRepository); // todoRepository가 notNull인지 확인

        log.info(todoRepository.getClass().getName());

    }

    // 데이터 베이스에 값 넣는 테스트
    @Test
    public void testInsert() {
        for (int i = 0; i < 100; i++) {

            Todo todo = Todo.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .dueDate(LocalDate.now())
                    .build();

            // .save()이게 jpa를 상속받아서 자동으로 만들어진 메소드임
            // save의 리턴 타입은 entity이다
            // query문 = insert into tbl_todo (complete, content, due_date, title) values (?,?,?,?)
            Todo result = todoRepository.save(todo);

            log.info(result);
        }

    }

    // 데이터 베이스에서 값 읽어오는 테스트
    @Test
    public void testRead() {
        Long tno = 1L;

        // .findById도 자동 생성되는 메소드임 id값을 받아서 db에서 찾아주는것
        // Optional은 값이 없을수도 null을 있으니 체크하기 위한 타입 =  NullPointerException을 방지해줌
        // 값이 null이여도 처리가 가능하도록 도와줌
        // query문 = select * from tdl_table where tno = 1
        Optional<Todo> result = todoRepository.findById(tno);

        // Optional로 값이 있으면 객체가 저장되고 아니면 null이므로 에러를 체크
        Todo todo = result.orElseThrow();

        log.info(todo);
    }

    // 데이터 베이스 값 수정하는 테스트
    @Test
    public void testUpdate() {
        Long tno = 1L;

        // [업데이트 순서]
        // 1. 해당 객체 불러옴
        Optional<Todo> result = todoRepository.findById(tno);

        Todo todo = result.orElseThrow();

        // 2. 변경
        // query문 = update tdl_todo set complete = ?, content = ?, title = ? where tno = ?
        todo.changeTitle("Update Title");
        todo.changeContent("Update Content");
        todo.changeComplete(true);

        // 3. 변경된 내용 저장
        todoRepository.save(todo);

    }

    @Test
    public void testPaging() {
        // 페이지 번호는 0부터 시작
        // jpa는 페이징을 위한 패키지를 제공해준다
        // Pageable : 페이징을 처리해주는 인터페이스
        // PageRequest.of() 페이징 요청하는 클래스
        Pageable pageable = PageRequest.of(0, 10, Sort.by("tno").descending());

        // Page : 페이징을 위한 클래스
        // .findAll(Pageable) = 기본적으로 페이징 처리를 지원해주고 파라미터로 Pageable을 넣어주면 페이지 타입으로 나옴
        Page<Todo> result = todoRepository.findAll(pageable);

        log.info(result.getTotalElements());

        log.info(result.getContent());
    }

}
