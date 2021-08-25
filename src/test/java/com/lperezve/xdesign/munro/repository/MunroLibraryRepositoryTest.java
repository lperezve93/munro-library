package com.lperezve.xdesign.munro.repository;

import com.lperezve.xdesign.munro.model.Munro;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
public class MunroLibraryRepositoryTest {
    @InjectMocks
    private MunroLibraryRepository munroLibraryRepository;

    /**
     * Method to test: saveData
     * What is the Scenario: Add the stored Munro List with the new data
     * What is the Result: The stored data has been added with new data
     */
    @Test
    public void saveData_loadCsvFile_dataUploaded() {
        // Given
        List<Munro> mockMunroList = checkMockMunroLitStatus();
        List<Munro> munroListToStore = createMunroList();

        // When
        List<Munro> munroList = munroLibraryRepository.saveData(mockMunroList);

        // Then
        assertThat(munroList, notNullValue());
        assertThat(munroList.size(), is(0));
        assertThat(munroListToStore, notNullValue());
        assertThat(munroListToStore.size(), greaterThan(0));
    }

    /**
     * Method to test: findMunros
     * What is the Scenario: Retrieve a list of Munros loaded from the file
     * What is the Result: Returns the list of Munros expected
     */
    @Test
    public void findMunros() {
        // When
        List<Munro> mockMunroList = munroLibraryRepository.findMunros();

        // Then
        assertThat(mockMunroList, notNullValue());
    }


    private List<Munro> checkMockMunroLitStatus() {
        try {
            Field field = MunroLibraryRepository.class.getDeclaredField("mockMunroList");
            field.setAccessible(true);
            return (List<Munro>) field.get(munroLibraryRepository);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Munro> createMunroList() {
        Munro munro_1 = new Munro();
        munro_1.setRunningNo(1);
        munro_1.setDoBIHNumber(1);
        munro_1.setStreetMap("http://www.streetmap.co.uk/newmap.srf?x=277324&y=730857&z=3&sv=277324,730857&st=4&tl=~&bi=~&lu=N&ar=y");
        munro_1.setGeograph("http://www.geograph.org.uk/gridref/NN7732430857");
        munro_1.setHillBagging("http://www.hill-bagging.co.uk/mountaindetails.php?qu=S&rf=1");
        munro_1.setName("Ben Chonzie");

        return Arrays.asList(munro_1);
    }
}

