package com.lperezve.xdesign.munro.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class Munro {

    @CsvBindByName(column = "Running No", required = true)
    private Integer runningNo;

    @CsvBindByName(column = "DoBIH Number", required = true)
    private Integer doBIHNumber;

    @CsvBindByName(column = "Streetmap", required = true)
    private String streetMap;

    @CsvBindByName(column = "Geograph", required = true)
    private String geograph;

    @CsvBindByName(column = "Hill-bagging", required = true)
    private String hillBagging;

    @CsvBindByName(column = "Name", required = true)
    private String name;

    @CsvBindByName(column = "SMC Section", required = true)
    private Integer smcSection;

    @CsvBindByName(column = "RHB Section", required = true)
    private String rhbSection;

    @CsvBindByName(column = "_Section", required = true)
    private float section;

    @CsvBindByName(column = "Height (m)", required = true)
    private float heightM;

    @CsvBindByName(column = "Height (ft)", required = true)
    private float heightFt;

    @CsvBindByName(column = "Map 1:50", required = true)
    private String map150;

    @CsvBindByName(column = "Map 1:25", required = true)
    private String map125;

    @CsvBindByName(column = "Grid Ref", required = true)
    private String gridRef;

    @CsvBindByName(column = "GridRefXY", required = true)
    private String grifRefXY;

    @CsvBindByName(column = "xcoord", required = true)
    private String xcoord;

    @CsvBindByName(column = "ycoord", required = true)
    private String ycoord;

    @CsvBindByName(column = "1891")
    private String post1891;

    @CsvBindByName(column = "1921")
    private String post1921;

    @CsvBindByName(column = "1933")
    private String post1933;

    @CsvBindByName(column = "1953")
    private String post1953;

    @CsvBindByName(column = "1969")
    private String post1969;

    @CsvBindByName(column = "1974")
    private String post1974;

    @CsvBindByName(column = "1981")
    private String post1981;

    @CsvBindByName(column = "1984")
    private String post1984;

    @CsvBindByName(column = "1990")
    private String post1990;

    @CsvBindByName(column = "1997")
    private String post1997;

    @CsvBindByName(column = "Post 1997")
    private String categoryPost1997; //change for characters???

    @CsvBindByName(column = "Comments")
    private String comments;

}
