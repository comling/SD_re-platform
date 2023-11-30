package kr.techdna.replatform2023.mapper;

import kr.techdna.replatform2023.dto.ResAsData;
import kr.techdna.replatform2023.dto.ResBusinessData;
import kr.techdna.replatform2023.dto.SearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface BusinessDataMapper {

    int count(final SearchDto params);
    Map<String, Object> countAndSumCapacity(final SearchDto params);

    List<ResBusinessData> getBusinessDataList(final SearchDto params);
    List<ResBusinessData> getSearchBusinessDataList(final SearchDto params);
    List<ResBusinessData> getSearchBusinessDataListForExcelDownload(final SearchDto params);
    List<ResAsData> selectForUserID(final Integer userID);
    int countForAsData(final Integer userID);

    long getSumCapacity();

    int getSumCount();

    HashMap<String, Object> getMinMaxYear();

    /* TODO: 아래 필터 리스트 조회에 대해 DISTINCT 결과값이 아닌 정형화된 별도의 값으로 관리하여야 함 */
    List<String> getSearchFilterBNAME();
    List<Integer> getSearchFilterBYEAR();
    List<String> getSearchFilterFacilityType();
    List<String> getSearchFilterEnergy();
    List<String> getSearchFilterSigungu();


}
