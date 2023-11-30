package kr.techdna.replatform2023.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TotDTO {
    private String sigungu;
    private int cnt;
    private double sum;
}
