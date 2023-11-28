package kr.techdna.replatform2023.service;

import kr.techdna.replatform2023.domain.BusinessData;
import kr.techdna.replatform2023.dto.PaginationDto;
import kr.techdna.replatform2023.dto.ReqBusinessData;
import kr.techdna.replatform2023.dto.ResBusinessData;
import kr.techdna.replatform2023.dto.SearchDto;
import kr.techdna.replatform2023.mapper.BusinessDataMapper;
import kr.techdna.replatform2023.repository.BusinessDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

}
