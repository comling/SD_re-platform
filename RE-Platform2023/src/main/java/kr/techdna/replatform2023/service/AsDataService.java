package kr.techdna.replatform2023.service;

import kr.techdna.replatform2023.dto.ResAsData;
import kr.techdna.replatform2023.mapper.AsDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AsDataService {

    private final AsDataMapper asDataMapper;

    public List<ResAsData> getAsDataForBusinessData(Integer userID){
        System.out.println(userID);
        List<ResAsData> list = asDataMapper.selectForUserID(userID);
        System.out.println(list);
        return list;
    }

}
