package com.lperezve.xdesign.munro.dto;

import com.lperezve.xdesign.munro.model.Munro;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MunroResponseDTO {

    private String name;

    private Float heightM;

    private String category;

    private String gridReference;

    public static MunroResponseDTO from(Munro munro) {
        return MunroResponseDTO.builder()
                .name(munro.getName())
                .heightM(munro.getHeightM())
                .category(munro.getCategoryPost1997())
                .gridReference(munro.getGridRef())
                .build();
    }

}