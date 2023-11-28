package kr.techdna.replatform2023.test;

import jakarta.persistence.EntityNotFoundException;
import kr.techdna.replatform2023.domain.UserData;
import kr.techdna.replatform2023.repository.UserDataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDataRepositoryTest {

    @Autowired
    UserDataRepository userDataRepository;

    @Test
    void saveUserData(){
        UserData saveParams = UserData.builder()
                .loginID("test")
                .loginPW("test")
                .userName("홍길동")
                .organization("안산시청")
                .phone("010-0000-0000")
                .email("0000@korea.kr")
                .build();

        UserData userData = userDataRepository.save(saveParams);
        Assertions.assertEquals(userData.getLoginID(), "test");
    }

    @Test
    void findAllUser(){
        userDataRepository.findAll();
    }

    @Test
    void findUserById(){
        UserData user = userDataRepository.findById(1).orElseThrow(() -> new EntityNotFoundException());
        Assertions.assertEquals(user.getLoginID(), "test");
    }

    @Test
    void deleteUserById(){
        userDataRepository.deleteById(1);
    }
}
