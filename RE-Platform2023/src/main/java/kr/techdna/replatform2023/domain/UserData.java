package kr.techdna.replatform2023.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "User_data")
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "No", nullable = false)
    private Integer No;

    @Column(name= "LoginID")
    private String LoginID;

    @Column(name= "LoginPW")
    private String LoginPW;

    @Column(name= "UserName")
    private String UserName;

    @Column(name= "Organization")
    private String Organization;

    @Column(name= "Phone")
    private String Phone;

    @Column(name= "Email")
    private String Email;

    @Builder

    public UserData(Integer no, String loginID, String loginPW, String userName, String organization, String phone, String email) {
        No = no;
        LoginID = loginID;
        LoginPW = loginPW;
        UserName = userName;
        Organization = organization;
        Phone = phone;
        Email = email;
    }
}