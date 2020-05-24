package pl.edu.agh.soa.lab1;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "LegoBlock")
@Data
public class LegoBlockDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long LegoBlockid;
    private String color;
    private Long partNumber;

}
