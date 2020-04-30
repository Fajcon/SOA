package pl.edu.agh.soa.lab1;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@JsonAutoDetect
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Test {
    @JsonProperty
    private String testString;
    @JsonProperty
    private Long testLong;
    @JsonProperty
    private LegoPack legoPack;
}
