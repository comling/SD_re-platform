package kr.techdna.replatform2023.dto;

import kr.techdna.replatform2023.domain.BusinessData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResBusinessData {

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

    public ResBusinessData(BusinessData businessData) {
        this.userID = businessData.getUserID();
        this.BYEAR = businessData.getBYEAR();
        this.BNAME = businessData.getBNAME();
        this.organization = businessData.getOrganization();
        this.department = businessData.getDepartment();
        this.applicantName = businessData.getApplicantName();
        this.applicantPhone = businessData.getApplicantPhone();
        this.capacity = businessData.getCapacity();
        this.applicantAddress = businessData.getApplicantAddress();
        this.address = businessData.getAddress();
        this.roadAddress = businessData.getRoadAddress();
        this.bCode = businessData.getBCode();
        this.hCode = businessData.getHCode();
        this.sigungu = businessData.getSigungu();
        this.eupMyeon = businessData.getEupMyeon();
        this.dong = businessData.getDong();
        this.energy = businessData.getEnergy();
        this.installType = businessData.getInstallType();
        this.constructionCompany = businessData.getConstructionCompany();
        this.facilityType = businessData.getFacilityType();
        this.monitoring = businessData.getMonitoring();
        this.constructionNumber = businessData.getConstructionNumber();
        this.entX = businessData.getEntX();
        this.entY = businessData.getEntY();
    }

}
