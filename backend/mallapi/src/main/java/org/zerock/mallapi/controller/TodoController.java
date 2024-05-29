package org.zerock.mallapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.zerock.mallapi.dto.PageRequestDTO;
import org.zerock.mallapi.dto.PageResponseDTO;
import org.zerock.mallapi.dto.TodoDTO;
import org.zerock.mallapi.service.TodoService;

import java.util.Map;

@RestController // 이걸 붙여줘야 이게 RESTfull한 controller인지 알 수 있음
@Log4j2 // 로그 출력을 위한것
@RequiredArgsConstructor
@RequestMapping("/api/todo") // 공통된 url을 사용하기 위한 것 / 이 밑에 있는것들은 이 rul뒤에 붙음
public class TodoController {
    private final TodoService todoService;

    // get 형식의 요청에서 쿼리 문자열을 전달하기 위해 사용되는 방법
    @GetMapping("/{tno}")
    public TodoDTO get(@PathVariable("tno") Long tno){ // 어떤 요청 값이 들어올지 모를 경우 사용
        // todoService에 있는 get을 사용해서 리턴해줌
        return todoService.get(tno);
    }

    // PageRequestDTO에 정의한 객체의 값이 ?뒤에 파라미터로 들어감 key=value형식
    // http://localhost:8080/api/todo/list?page=2&size=10
    @GetMapping("/list")
    public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO) {
        log.info("list..........." + pageRequestDTO);
        return todoService.getList(pageRequestDTO);
    }

    // data를 받는것
    @PostMapping("/")
    public Map<String, Long> register(@RequestBody TodoDTO dto){ // body로 값을 받음
        log.info("todoDTO: " + dto);

        Long tno = todoService.register(dto);

        return Map.of("TNO", tno);
    }

    @PutMapping("/{tno}")
    public Map<String, String> modify(@PathVariable("tno") Long tno, @RequestBody TodoDTO todoDTO){
        todoDTO.setTno(tno);
        todoService.modify(todoDTO);

        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/{tno}")
    public Map<String, String> remove(@PathVariable Long tno){
        todoService.remove(tno);

        return Map.of("RESULT", "SUCCESS");
    }
}
