package kr.techdna.replatform2023.mapper;

import kr.techdna.replatform2023.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface BusinessDataMapper {

    int count(final SearchDto params);
    Map<String, Object> countAndSumCapacity(final SearchDto params);
    List<cntKindDTO> energyCountAndSumCapacity(final SearchDto params);
    List<cntKindDTO> bnameCountAndSumCapacity(final SearchDto params);
    List<TotDTO> selectMainAggr();

    List<ResBusinessData> getBusinessDataList(final SearchDto params);
    List<ResBusinessData> getSearchBusinessDataList(final SearchDto params);
    List<ResBusinessData> getSearchBusinessDataListForExcelDownload(final SearchDto params);
    List<ResAsData> selectForUserID(final Integer userID);
    List<ResAsDataJoinBusinessData> selectASdataJoinBusinessData(final SearchDto params);
    List<ResAsDataJoinBusinessData> selectASdataJoinBusinessDataForExcelDownload(final SearchDto params);

    int countForAsData(final Integer userID);
    int countForAsDataJoinBusinessData(final SearchDto params);

    long getSumCapacity();

    int getSumCount();

    HashMap<String, Object> getMinMaxYear();

    /* TODO: 아래 필터 리스트 조회에 대해 DISTINCT 결과값이 아닌 정형화된 별도의 값으로 관리하여야 함 */
    List<String> getSearchFilterBNAME();
    List<Integer> getSearchFilterBYEAR();
    List<String> getSearchFilterFacilityType();
    List<String> getSearchFilterEnergy();
    List<String> getSearchFilterSigungu();
    List<String> getSearchFilterEupMyeon(String sigungu);


}
