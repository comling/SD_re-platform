package kr.techdna.replatform2023.repository;

import kr.techdna.replatform2023.dto.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepository extends JpaRepository<UserData, Integer> {
}
