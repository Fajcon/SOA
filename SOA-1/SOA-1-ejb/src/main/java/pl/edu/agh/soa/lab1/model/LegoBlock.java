package pl.edu.agh.soa.lab1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LegoBlock {
    private String color;
    private String partNumber;
    private String name;
}
