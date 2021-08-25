package com.lperezve.xdesign.munro.controller;

import com.lperezve.xdesign.munro.dto.MunroResponseDTO;
import com.lperezve.xdesign.munro.exception.MunroCategoryException;
import com.lperezve.xdesign.munro.exception.MunroLimitException;
import com.lperezve.xdesign.munro.service.MunroLibraryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class MunroLibraryControllerTest {

    @InjectMocks
    private MunroLibraryController munroLibraryController;

    @Mock
    private MunroLibraryService munroLibraryServiceMock;

    @Test
    public void findMunros_successfulServiceCall_validMunroResponseDto() throws MunroLimitException, MunroCategoryException {
        // Given
        List<MunroResponseDTO> munroResponseDTOListService = createMunroResponseDtoList();
        given(munroLibraryServiceMock.findMunros(any(), any(), any(), any(), any())).willReturn(munroResponseDTOListService);

        // When
        ResponseEntity responseEntity = munroLibraryController.findMunros("MUN", "name,desc", null, null, null);
        List<MunroResponseDTO> munroResponseDTOList = (List<MunroResponseDTO>) responseEntity.getBody();

        assertThat(responseEntity, notNullValue());
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(munroResponseDTOList, notNullValue());
        assertThat(munroResponseDTOList.size(), is(2));

        assertThat(munroResponseDTOList.get(0).getCategory(), is("MUN"));
        assertThat(munroResponseDTOList.get(0).getHeightM(), is(Float.valueOf(950)));
        assertThat(munroResponseDTOList.get(0).getName(), is("The Saddle"));
        assertThat(munroResponseDTOList.get(0).getGridReference(), is("NG936131"));

    }

    private List<MunroResponseDTO> createMunroResponseDtoList() {
        List<MunroResponseDTO> munroResponseDTOList = new ArrayList<>();
        MunroResponseDTO munroResponseDTOService_1 = MunroResponseDTO.builder()
                .category("MUN")
                .heightM(Float.valueOf(950))
                .name("The Saddle")
                .gridReference("NG936131")
                .build();

        MunroResponseDTO munroResponseDTOService_2 = MunroResponseDTO.builder()
                .category("MUN")
                .heightM(Float.valueOf(973))
                .name("Creag nan Damh")
                .gridReference("NG983112")
                .build();

        munroResponseDTOList.add(munroResponseDTOService_1);
        munroResponseDTOList.add(munroResponseDTOService_2);
        return munroResponseDTOList;
    }
}
