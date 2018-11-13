package pl.gda.pg.eti.kask.javaee.jsf.business.entities;

import lombok.*;
import pl.gda.pg.eti.kask.javaee.jsf.api.Resource;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Beer {
    private Long id;
    private String name;
    private Double voltage;
    private Integer IBU;

    public Beer(Long id, String name, Double voltage, Integer IBU) {
        this.id = id;
        this.name = name;
        this.voltage = voltage;
        this.IBU = IBU;
    }

    private List<Resource> links = new ArrayList<>();
}
