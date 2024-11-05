package com.xg.w3.aptos.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqCardDto {
    private List<Integer> result;
    private String md5;
    private String key;
}
