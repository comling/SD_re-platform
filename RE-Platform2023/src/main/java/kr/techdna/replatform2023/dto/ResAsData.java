package kr.techdna.replatform2023.dto;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResAsData {

    private Integer no;

    private Integer userID;

    private Date asDate;

    private String asDescription;

    private String asStatus;

    @Override
    public String toString() {
        return "ResAsData{" +
                "no=" + no +
                ", userID=" + userID +
                ", asDate=" + asDate +
                ", asDescription=" + asDescription +
                ", asStatus='" + asStatus + '\'' +
                '}';
    }
}
