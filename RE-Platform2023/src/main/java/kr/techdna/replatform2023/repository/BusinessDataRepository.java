package kr.techdna.replatform2023.repository;

import kr.techdna.replatform2023.dto.BusinessData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessDataRepository extends JpaRepository<BusinessData, Integer> {
}
