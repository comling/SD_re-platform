package kr.techdna.replatform2023.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResAsDataJoinBusinessData {

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
    private Integer no;
    private Date asDate;
    private String asDescription;
    private String asStatus;

    @Override
    public String toString() {
        return "ResAsDataJoinBusinessData{" +
                "userID=" + userID +
                ", BYEAR=" + BYEAR +
                ", BNAME='" + BNAME + '\'' +
                ", organization='" + organization + '\'' +
                ", department='" + department + '\'' +
                ", applicantName='" + applicantName + '\'' +
                ", applicantPhone='" + applicantPhone + '\'' +
                ", capacity=" + capacity +
                ", applicantAddress='" + applicantAddress + '\'' +
                ", address='" + address + '\'' +
                ", roadAddress='" + roadAddress + '\'' +
                ", bCode='" + bCode + '\'' +
                ", hCode='" + hCode + '\'' +
                ", sigungu='" + sigungu + '\'' +
                ", eupMyeon='" + eupMyeon + '\'' +
                ", dong='" + dong + '\'' +
                ", energy='" + energy + '\'' +
                ", installType='" + installType + '\'' +
                ", constructionCompany='" + constructionCompany + '\'' +
                ", facilityType='" + facilityType + '\'' +
                ", monitoring='" + monitoring + '\'' +
                ", constructionNumber='" + constructionNumber + '\'' +
                ", entX=" + entX +
                ", entY=" + entY +
                ", no=" + no +
                ", asDate=" + asDate +
                ", asDescription='" + asDescription + '\'' +
                ", asStatus='" + asStatus + '\'' +
                '}';
    }
}
