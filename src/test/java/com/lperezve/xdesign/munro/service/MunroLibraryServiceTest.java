package com.lperezve.xdesign.munro.service;

import com.lperezve.xdesign.munro.dto.GlobalResponseDTO;
import com.lperezve.xdesign.munro.dto.MunroResponseDTO;
import com.lperezve.xdesign.munro.model.Munro;
import com.lperezve.xdesign.munro.repository.MunroLibraryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class MunroLibraryServiceTest {

    @InjectMocks
    private MunroLibraryService munroLibraryService;

    @Mock
    private MunroLibraryRepository munroLibraryRepository;

    /**
     * Method to test: saveData
     * What is the Scenario: Successful data saved
     * What is the Result: Returns a GlobalResponseDTO with expected description
     */
    @Test
    public void saveData_validGlobalResponseDto() throws FileNotFoundException, URISyntaxException {
        // When
        GlobalResponseDTO globalResponseDTO = munroLibraryService.saveData();

        // Then
        assertThat(globalResponseDTO, notNullValue());
        assertThat(globalResponseDTO.getDescription(), is("SUCCESS: data loaded"));
    }

    /**
     * Method to test: findMunros
     * What is the Scenario: Retrieve the list of Munro given empty filters
     * What is the Result: Returns the MunroResponseDTO expected
     */
    @Test
    public void findMunros_emptyFilter_validMunroResponseDTO() {
        // Given
        List<Munro> mockMunroList = createMunroList();
        given(munroLibraryRepository.findMunros()).willReturn(mockMunroList);

        // When
        List<MunroResponseDTO> munroResponseDTOList = munroLibraryService.findMunros(null, null, null, null, null);

        // Then
        assertThat(munroResponseDTOList, notNullValue());
        assertThat(munroResponseDTOList.size(), is(5));
    }

    /**
     * Method to test: findMunros
     * What is the Scenario: Retrieve the list of Munro given a category to filter
     * What is the Result: Returns the MunroResponseDTO expected
     */
    @Test
    public void findMunros_filterByCategory_validMunroResponseDTO() {
        // Given
        List<Munro> mockMunroList = createMunroList();
        given(munroLibraryRepository.findMunros()).willReturn(mockMunroList);

        // When
        String categoryMun = "MUN";
        String categoryTOP = "TOP";
        List<MunroResponseDTO> munroResponseDTOListMUN = munroLibraryService.findMunros(categoryMun, null, null, null, null);
        List<MunroResponseDTO> munroResponseDTOListTOP = munroLibraryService.findMunros(categoryTOP, null, null, null, null);

        // Then
        assertThat(munroResponseDTOListMUN, notNullValue());
        assertThat(munroResponseDTOListMUN.size(), is(3));

        assertThat(munroResponseDTOListTOP, notNullValue());
        assertThat(munroResponseDTOListTOP.size(), is(2));
    }

    /**
     * Method to test: findMunros
     * What is the Scenario: Retrieve the list of Munro given a limit to get the results
     * What is the Result: Returns the MunroResponseDTO expected
     */
    @Test
    public void findMunros_filterLimit_validMunroResponseDTO() {
        // Given
        List<Munro> mockMunroList = createMunroList();
        given(munroLibraryRepository.findMunros()).willReturn(mockMunroList);

        // When
        String limit = "2";
        List<MunroResponseDTO> munroResponseDTOList = munroLibraryService.findMunros(null, null, limit, null, null);

        assertThat(munroResponseDTOList.size(), is(2));
    }

    private List<Munro> createMunroList() {
        Munro munro_1 = new Munro();
        munro_1.setRunningNo(1);
        munro_1.setDoBIHNumber(1);
        munro_1.setName("Ben Chonzie");
        munro_1.setHeightM(750);
        munro_1.setCategoryPost1997("MUN");

        Munro munro_2 = new Munro();
        munro_2.setRunningNo(2);
        munro_2.setDoBIHNumber(2);
        munro_2.setName("Stob Binnein");
        munro_2.setHeightM(720);
        munro_2.setCategoryPost1997("MUN");

        Munro munro_3 = new Munro();
        munro_3.setRunningNo(3);
        munro_3.setDoBIHNumber(3);
        munro_3.setName("An Caisteal");
        munro_3.setHeightM(945);
        munro_3.setCategoryPost1997("MUN");

        Munro munro_4 = new Munro();
        munro_4.setRunningNo(4);
        munro_4.setDoBIHNumber(4);
        munro_4.setName("Ben Lawers - Creag an Fhithich");
        munro_4.setHeightM(876);
        munro_4.setCategoryPost1997("TOP");

        Munro munro_5 = new Munro();
        munro_5.setRunningNo(5);
        munro_5.setDoBIHNumber(5);
        munro_5.setName("Carn Liath - Stob Coire Dubh");
        munro_5.setHeightM(890);
        munro_5.setCategoryPost1997("TOP");

        Munro munro_6 = new Munro();
        munro_6.setRunningNo(6);
        munro_6.setDoBIHNumber(6);
        munro_6.setName("The Saddle - Trig Point");
        munro_6.setHeightM(800);
        munro_6.setCategoryPost1997("");

        return Arrays.asList(munro_1, munro_2, munro_3, munro_4, munro_5, munro_6);
    }

}
