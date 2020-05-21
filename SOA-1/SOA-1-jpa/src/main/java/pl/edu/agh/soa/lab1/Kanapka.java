package pl.edu.agh.soa.lab1;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "kanapka")
@Data
public class Kanapka {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

}
