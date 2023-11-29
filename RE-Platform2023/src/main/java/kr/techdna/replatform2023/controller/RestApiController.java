package kr.techdna.replatform2023.controller;

import jakarta.servlet.http.HttpServletResponse;
import kr.techdna.replatform2023.dto.ResBusinessData;
import kr.techdna.replatform2023.dto.SearchDto;
import kr.techdna.replatform2023.service.BusinessDataService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RestApiController {

    private final BusinessDataService businessDataService;

    /**
     *  홈 화면 조회용
     * @param params
     * SearchDto {
     *     pageNum : 현재 페이지
     *     size : 한 페이지에 보여질 목록 개수
     *     pageSize : 하단에 보여질 페이지 개수
     *     keyword : 검색 타입
     *     PaginationDto : 페이지네이션 정보
     *     searchFilter : 검색 조건
     *              {
     *                  BNAME : 사업명,
     *                  BYEAR : 사업연도,
     *                  energy : 에너지원,
     *                  facilityType : 사업구분,
     *                  sigungu : 시/군/구
     *              }
     * }*/
    @GetMapping("/getHomeBusinessDataList")
    public Map<String, Object> getMainTotalCnt(final SearchDto params){
        return businessDataService.getBusinessDataList(params);
    }

    /**
     * @param params
     * SearchDto {
     *     pageNum : 현재 페이지
     *     size : 한 페이지에 보여질 목록 개수
     *     pageSize : 하단에 보여질 페이지 개수
     *     keyword : 검색 타입
     *     PaginationDto : 페이지네이션 정보
     *     searchFilter : 검색 조건
     *              {
     *                  BNAME : 사업명,
     *                  BYEAR : 사업연도,
     *                  energy : 에너지원,
     *                  facilityType : 사업구분,
     *                  sigungu : 시/군/구
     *              }
     * }*/
    @PostMapping("/getSearchBusinessDataList")
    public Map<String, Object> getSearchBusinessDataList(@RequestBody final SearchDto params){
        return businessDataService.getSearchBusinessDataList(params);
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


    /* 검색 필터 로드 */
    @GetMapping("/getSearchFilter")
    public Map<String, Object> getSearchFilter(){
        return businessDataService.getSearchFilter();
    }


    /* Excel download */
    @GetMapping("/excelDownload")
    public void excelDownload(final SearchDto params, HttpServletResponse response) throws InvalidFormatException, IOException {
            Map<String, Object> data = businessDataService.getBusinessDataList(params);
            businessDataService.excelDataDownload(data, response);
    }



}
