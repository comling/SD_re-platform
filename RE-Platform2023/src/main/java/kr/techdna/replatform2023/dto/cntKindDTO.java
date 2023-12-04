package kr.techdna.replatform2023.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class cntKindDTO {
    private String kind;
    private int count;
    private double sum;
}
