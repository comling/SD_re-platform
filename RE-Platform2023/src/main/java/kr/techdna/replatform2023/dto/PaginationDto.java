package kr.techdna.replatform2023.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationDto {
    private int totalRecordCount;   // 전체 데이터 수
    private int totalPageCount;     // 전체 페이지 수
    private int startPage;          // 첫 페이지 번호
    private int endPage;            // 끝 페이지 번호
    private int limitStart;         // LIMIT 시작 위치
    private boolean existPrevPage;  // 이전 페이지 존재 여부
    private boolean existNextPage;  // 다음 페이지 존재 여부

    private double totalSumCapacity; // 전체 발전 용량(Capacity) 합계

    public PaginationDto(int totalRecordCount, SearchDto searchDto) {
        if (totalRecordCount > 0) {
            this.totalRecordCount = totalRecordCount;
            this.calculation(searchDto);
        }
    }

    public PaginationDto(int totalRecordCount, double totalSumCapacity, SearchDto searchDto) {
        if (totalRecordCount > 0) {
            this.totalRecordCount = totalRecordCount;
            this.totalSumCapacity = totalSumCapacity;
            this.calculation(searchDto);
        }
    }

    private void calculation(SearchDto searchDto) {

        // 전체 페이지 수 계산
        totalPageCount = ((totalRecordCount - 1) / searchDto.getSize()) + 1;

        // 현재 페이지 번호가 전체 페이지 수보다 큰 경우, 현재 페이지 번호에 전체 페이지 수 저장
        if (searchDto.getPageNum() > totalPageCount) {
            searchDto.setPageNum(totalPageCount);
        }

        // 첫 페이지 번호 계산
        startPage = ((searchDto.getPageNum() - 1) / searchDto.getPageSize()) * searchDto.getPageSize() + 1;

        // 끝 페이지 번호 계산
        endPage = startPage + searchDto.getPageSize() - 1;

        // 끝 페이지가 전체 페이지 수보다 큰 경우, 끝 페이지 전체 페이지 수 저장
        if (endPage > totalPageCount) {
            endPage = totalPageCount;
        }

        // LIMIT 시작 위치 계산
        limitStart = (searchDto.getPageNum() - 1) * searchDto.getSize();

        // 이전 페이지 존재 여부 확인
        existPrevPage = startPage != 1;

        // 다음 페이지 존재 여부 확인
        existNextPage = (endPage * searchDto.getSize()) < totalRecordCount;
    }
}
