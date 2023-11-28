package kr.techdna.replatform2023.repository;

import kr.techdna.replatform2023.domain.BusinessData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface BusinessDataRepository extends JpaRepository<BusinessData, Integer>, JpaSpecificationExecutor<BusinessData> {

        Page<BusinessData> findAll(Pageable pageable);
}

