package kr.techdna.replatform2023.controller;

import kr.techdna.replatform2023.dto.ResBusinessData;
import kr.techdna.replatform2023.dto.SearchDto;
import kr.techdna.replatform2023.service.BusinessDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RestApiController {

    private final BusinessDataService businessDataService;

    /**
     * @param SearchDto
     * {
     *     pageNum : 현재 페이지
     *     size : 한 페이지에 보여질 목록 개수
     *     pageSize : 하단에 보여질 페이지 개수
     *     keyword : 검색 타입
     *     PaginationDto : 페이지네이션 정보
     * }*/
    @GetMapping("/getBusinessDataList")
    public Map<String, Object> getMainTotalCnt(final SearchDto params){
        return businessDataService.getBusinessDataList(params);
    }

    @GetMapping("/getMinMaxYear")
    public Map<String, Object> getMinMaxYear(){
        return businessDataService.getMinMaxYear();
    }

    @GetMapping("/getSumCapacity")
    public Long getSumCapacity(){
        return businessDataService.getSumCapacity();
    }

    @GetMapping("/getSumCount")
    public int getSumCount(final SearchDto params){
        return businessDataService.getSumCount(params);
    }



}
