package kr.techdna.replatform2023.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class SearchDto {

    private int pageNum;        // 현재 페이지
    private int size;           // 한 페이지에 보여질 목록 개수
    private int pageSize;       // 하단에 보여질 페이지 개수
    private String keyword;     // 검색어
    private Map<String, Object> searchFilter;  // 검색 타입 Object
    private PaginationDto pagination;  // 페이지네이션 정보

    public SearchDto(){
        this.pageNum = 1;
        this.size = 10;
        this.pageSize = 10;
    }

    public int getOffset(){
        return (pageNum - 1) * size;
    }

    @Override
    public String toString() {
        return "SearchDto{" +
                "pageNum=" + pageNum +
                ", size=" + size +
                ", pageSize=" + pageSize +
                ", keyword='" + keyword + '\'' +
                ", searchFilter=" + searchFilter +
                ", pagination=" + pagination +
                '}';
    }
}
