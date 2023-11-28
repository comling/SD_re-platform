package kr.techdna.replatform2023.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Business_data")
public class BusinessData {

    @Id
    @Column(name="UserID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;

    @Column(name="BYEAR")
    private int BYEAR;

    @Column(name="BNAME")
    private String BNAME;

    @Column(name="Organization")
    private String organization;

    @Column(name="Department")
    private String department;

    @Column(name="ApplicantName")
    private String applicantName;

    @Column(name="ApplicantPhone")
    private String applicantPhone;
    @Column(name="Capacity")
    private float capacity;

    @Column(name="ApplicantAddress")
    private String applicantAddress;

    @Column(name="Address")
    private String address;

    @Column(name="RoadAddress")
    private String roadAddress;

    @Column(name="Bcode")
    private String bCode;

    @Column(name="Hcode")
    private String hCode;

    @Column(name="Sigungu")
    private String sigungu;

    @Column(name="EupMyeon")
    private String eupMyeon;

    @Column(name="Dong")
    private String dong;

    @Column(name="Energy")
    private String energy;

    @Column(name="InstallType")
    private String installType;

    @Column(name="ConstructionCompany")
    private String constructionCompany;

    @Column(name="FacilityType")
    private String facilityType;

    @Column(name="Monitoring")
    private String monitoring;

    @Column(name="ConstructionNumber")
    private String constructionNumber;

    @Column(name="entX")
    private float entX;

    @Column(name="entY")
    private float entY;

    @Builder
    public BusinessData(int userID, int BYEAR, String BNAME, String organization, String department, String applicantName, String applicantPhone, float capacity, String applicantAddress, String address, String roadAddress, String bCode, String hCode, String sigungu, String eupMyeon, String dong, String energy, String installType, String constructionCompany, String facilityType, String monitoring, String constructionNumber, float entX, float entY) {
        this.userID = userID;
        this.BYEAR = BYEAR;
        this.BNAME = BNAME;
        this.organization = organization;
        this.department = department;
        this.applicantName = applicantName;
        this.applicantPhone = applicantPhone;
        this.capacity = capacity;
        this.applicantAddress = applicantAddress;
        this.address = address;
        this.roadAddress = roadAddress;
        this.bCode = bCode;
        this.hCode = hCode;
        this.sigungu = sigungu;
        this.eupMyeon = eupMyeon;
        this.dong = dong;
        this.energy = energy;
        this.installType = installType;
        this.constructionCompany = constructionCompany;
        this.facilityType = facilityType;
        this.monitoring = monitoring;
        this.constructionNumber = constructionNumber;
        this.entX = entX;
        this.entY = entY;
    }
}
