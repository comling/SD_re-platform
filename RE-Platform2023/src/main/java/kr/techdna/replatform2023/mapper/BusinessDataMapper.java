package kr.techdna.replatform2023.mapper;

import kr.techdna.replatform2023.dto.ResBusinessData;
import kr.techdna.replatform2023.dto.SearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface BusinessDataMapper {

    int count(final SearchDto params);

    List<ResBusinessData> getBusinessDataList(final SearchDto params);

    HashMap<String, Object> getMinMaxYear();

    long getSumCapacity();

    int getSumCount();
}
