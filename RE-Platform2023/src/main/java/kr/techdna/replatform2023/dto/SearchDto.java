package kr.techdna.replatform2023.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDto {

    private int pageNum;        // 현재 페이지
    private int size;           // 한 페이지에 보여질 목록 개수
    private int pageSize;       // 하단에 보여질 페이지 개수
    private String keyword;     // 검색어
    private String searchType;  // 검색 타입
    private PaginationDto pagination;  // 페이지네이션 정보

    public SearchDto(){
        this.pageNum = 1;
        this.size = 10;
        this.pageSize = 10;
    }

    public int getOffset(){
        return (pageNum - 1) * size;
    }

}