package pl.edu.agh.soa.lab1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LegoPack {
    private LegoBlock legoBlocks;
    private Long numberOfBlocks;
}
