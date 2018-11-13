package pl.gda.pg.eti.kask.javaee.jsf.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {
    private int from;
    private int to;

    public void normalizeWithSize(int size) {
        if (from < 0) {
            from = 0;
        }
        if (to > size - 1) {
            to = size - 1;
        }


        if (from > size - 1) {
            from = size - 1;
        }
        if (to < 0) {
            to = 0;
        }
    }
}
