package org.zerock.mallapi.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResponseDTO<E> {

    // dto목록
    private List<E> dtoList;

    private List<Integer> pageNumList;

    private PageRequestDTO pageRequestDTO;

    private boolean prev, next;

    private int totalCount, prevPage, nextPage, totalPage, current;

    // 생성자
    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(List<E> dtoList, PageRequestDTO pageRequestDTO, long totalCount){

        this.dtoList = dtoList;
        this.pageRequestDTO = pageRequestDTO;
        this.totalCount = (int)totalCount;

        // end 끝페이지
        // int로 만듬(올림(현재 페이지 / 10.0)) * 10
        int end = (int)(Math.ceil(pageRequestDTO.getPage() / 10.0)) * 10;

        int start = end - 9;

        // 진짜 마지막 페이지, end랑 확인해야됨
        // int로 만듬(올림(게시물 갯수/페이지 사이즈(소수로 나올수 있으니 double임)))
        int last = (int)(Math.ceil(totalCount/(double)pageRequestDTO.getSize()));

        // 진짜 마지막 페이지보다 끝페이지가 클 경우엔 진짜 마지막 페이지로 적용
        end = end > last ? last : end;

        // 스타트 값이 1보다만 크면 됨
        this.prev = start > 1;

        // 총 게시물 값이 현재 끝 값 * 페이지 사이즈보다 크다면 next 버튼 활성화
        this.next = totalCount > end * pageRequestDTO.getSize();

        // 구해놓은 첫 값과 끝 값 사이의 숫자들을 리스트로 저장
        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

        // 이전 페이지의 번호 ex) 11페이지 일 경우 10페이지
        this.prevPage = prev ? start -1 : 0;

        this.nextPage = next ? end + 1 : 0;

        this.totalPage = this.pageNumList.size();

        this.current = pageRequestDTO.getPage();

    }
}
