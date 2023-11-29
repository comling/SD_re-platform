package kr.techdna.replatform2023.service;

import jakarta.servlet.http.HttpServletResponse;
import kr.techdna.replatform2023.domain.BusinessData;
import kr.techdna.replatform2023.dto.PaginationDto;
import kr.techdna.replatform2023.dto.ResBusinessData;
import kr.techdna.replatform2023.dto.SearchDto;
import kr.techdna.replatform2023.mapper.BusinessDataMapper;
import kr.techdna.replatform2023.repository.BusinessDataRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BusinessDataService {

    private final BusinessDataRepository businessDataRepository;
    private final BusinessDataMapper businessDataMapper;

    public List<ResBusinessData> findAll(){
        Sort sort = Sort.by(Sort.Direction.DESC, "userID");
        List<BusinessData> list = businessDataRepository.findAll(sort);
        return list.stream().map(ResBusinessData::new).collect(Collectors.toList());
    }

    public Page<ResBusinessData> findByParams(Pageable pageable){
        return businessDataRepository.findAll(pageable).map(ResBusinessData::new);
    }

    public Map<String, Object> getBusinessDataList(SearchDto params){
        // 게시글 수 조회
        int count = businessDataMapper.count(params);

        // 등록된 게시글이 없는 경우, 로직 종료
        if (count < 1) {
            return Collections.emptyMap();
        }

        // 페이지네이션 정보 계산
        PaginationDto pagination = new PaginationDto(count, params);
        params.setPagination(pagination);

        // 게시글 리스트 조회
        List<ResBusinessData> list = businessDataMapper.getBusinessDataList(params);

        // 데이터 반환
        Map<String, Object> response = new HashMap<>();
        response.put("params", params);
        response.put("list", list);
        return response;
    }

    public Map<String, Object> getMinMaxYear(){
        Map<String, Object> response = new HashMap<>();
        response.put("list", businessDataMapper.getMinMaxYear());
        return response;
    }

    public long getSumCapacity(){
        return businessDataMapper.getSumCapacity();
    }

    public int getSumCount(SearchDto params){
        return businessDataMapper.count(params);
    }

    public Map<String, Object> getSearchFilter(){
        HashMap<String, Object> searchFilter = new HashMap<>();
        searchFilter.put("BNAME", businessDataMapper.getSearchFilterBNAME());
        searchFilter.put("BYEAR", businessDataMapper.getSearchFilterBYEAR());
        searchFilter.put("facilityType", businessDataMapper.getSearchFilterFacilityType());
        searchFilter.put("energy", businessDataMapper.getSearchFilterEnergy());
        searchFilter.put("sigungu", businessDataMapper.getSearchFilterSigungu());
        return searchFilter;
    }

    public Map<String, Object> getSearchBusinessDataList(SearchDto params){
        Map<String, Object> response = new HashMap<>();

        // 게시글 수 & 총 발전 용량 조회
        Map<String, Object> cntAndSumCapacity = businessDataMapper.countAndSumCapacity(params);
        int count = Long.valueOf(cntAndSumCapacity.get("count").toString()).intValue();
        // 등록된 게시글이 없는 경우, 로직 종료
        if (count < 1) {
            response.put("params", params);
            response.put("list", new ArrayList<>());
            return response;
        }
        double sumCapacity = (double) cntAndSumCapacity.get("sum");

        // 페이지네이션 정보 계산 & 총 발전 용량 값 전달
        PaginationDto pagination = new PaginationDto(count, sumCapacity, params);
        params.setPagination(pagination);

        // 게시글 리스트 조회
        List<ResBusinessData> list = businessDataMapper.getSearchBusinessDataList(params);

        // 데이터 반환
        response.put("params", params);
        response.put("list", list);
        return response;
    }


    /* Excel download */
    public void excelDataDownload(Map<String, Object> param, HttpServletResponse response) throws IOException {

        ClassPathResource classPathResource = new ClassPathResource("static/templates/excel-template.xlsx");

        try{
            InputStream is = new BufferedInputStream(classPathResource.getInputStream());
            OPCPackage opcPackage = OPCPackage.open(is);
            XSSFWorkbook wb = new XSSFWorkbook(opcPackage);
            Sheet sheet = wb.getSheet(wb.getSheetName(0));

            int totCnt = ((List<ResBusinessData>) param.get("list")).size();
            int i = 1;
            for (ResBusinessData item : (List<ResBusinessData>) param.get("list")) {
                setValue(sheet, "A" + i, item.getUserID().toString());
                setValue(sheet, "B" + i, String.valueOf(item.getBYEAR()));
                setValue(sheet, "C" + i, item.getBNAME());
                setValue(sheet, "D" + i, item.getOrganization());
                setValue(sheet, "E" + i, item.getDepartment());
                setValue(sheet, "F" + i, item.getApplicantName());
                setValue(sheet, "G" + i, item.getApplicantPhone());
                setValue(sheet, "H" + i, String.valueOf(item.getCapacity()));
                setValue(sheet, "I" + i, item.getApplicantAddress());
                setValue(sheet, "J" + i, item.getAddress());
                setValue(sheet, "K" + i, item.getRoadAddress());
                setValue(sheet, "L" + i, item.getBCode());
                setValue(sheet, "M" + i, item.getHCode());
                setValue(sheet, "N" + i, item.getSigungu());
                setValue(sheet, "O" + i, item.getEupMyeon());
                setValue(sheet, "P" + i, item.getDong());
                setValue(sheet, "Q" + i, item.getEnergy());
                setValue(sheet, "R" + i, item.getInstallType());
                setValue(sheet, "S" + i, item.getConstructionCompany());
                setValue(sheet, "T" + i, item.getFacilityType());
                setValue(sheet, "U" + i, item.getMonitoring());
                setValue(sheet, "V" + i, item.getConstructionNumber());
                setValue(sheet, "W" + i, String.valueOf(item.getEntX()));
                setValue(sheet, "X" + i, String.valueOf(item.getEntY()));
                i++;
            }


            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("re-platform-data", "UTF-8") + ".xlsx");
            response.setHeader("Content-Transfer-Encoding","binary");
            response.setStatus(200);
            wb.write(response.getOutputStream());
            wb.close();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    private void setValue(Sheet sheet, String position, String value) {
        CellReference ref = new CellReference(position);
        Row r = sheet.getRow(ref.getRow());
        if (r != null) {
            Cell c = r.getCell(ref.getCol());
            c.setCellValue(value);
        }
    }


}
