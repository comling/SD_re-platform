package kr.techdna.replatform2023.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import kr.techdna.replatform2023.domain.BusinessData;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReqBusinessData {

    private Integer userID;
    private int BYEAR;
    private String BNAME;
    private String organization;
    private String department;
    private String applicantName;
    private String applicantPhone;
    private float capacity;
    private String applicantAddress;
    private String address;
    private String roadAddress;
    private String bCode;
    private String hCode;
    private String sigungu;
    private String eupMyeon;
    private String dong;
    private String energy;
    private String installType;
    private String constructionCompany;
    private String facilityType;
    private String monitoring;
    private String constructionNumber;
    private float entX;
    private float entY;

    public BusinessData toEntity(){
        return BusinessData.builder()
                .userID(userID)
                .BYEAR(BYEAR)
                .BNAME(BNAME)
                .organization(organization)
                .department(department)
                .applicantName(applicantName)
                .applicantPhone(applicantPhone)
                .capacity(capacity)
                .applicantAddress(applicantAddress)
                .address(address)
                .roadAddress(roadAddress)
                .bCode(bCode)
                .hCode(hCode)
                .sigungu(sigungu)
                .eupMyeon(eupMyeon)
                .dong(dong)
                .energy(energy)
                .installType(installType)
                .constructionCompany(constructionCompany)
                .facilityType(facilityType)
                .monitoring(monitoring)
                .constructionNumber(constructionNumber)
                .entX(entX)
                .entY(entY)
                .build();
    }

}
