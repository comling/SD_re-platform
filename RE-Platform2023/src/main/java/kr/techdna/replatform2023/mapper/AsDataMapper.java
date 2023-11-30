package kr.techdna.replatform2023.mapper;

import kr.techdna.replatform2023.dto.ResAsData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AsDataMapper {

    /* BusinessData.UserID 값을 받아서 해당 AS 데이터 조회 */
    List<ResAsData> selectForUserID(Integer userID);

}
