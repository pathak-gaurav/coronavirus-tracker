package ca.gauravpathak.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LocationStats {

    private String province;
    private String country;
    private int latestTotalCases;
    private int casesIncreased;
}
