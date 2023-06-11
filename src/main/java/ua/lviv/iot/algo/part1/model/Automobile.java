package ua.lviv.iot.algo.part1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Automobile {
    public static final String HEADERS =
            "id; model; address;endOfRent;Problems";
    private Integer id;
    private String model;
    private String address;
    private String endOfRent;
    private String problems;

    public String getHeaders() {
        return HEADERS;
    }

    public String toSCV() {
        return getId() + ";"
                + getModel() + ";"
                + getAddress() + ";"
                + getEndOfRent() + ";"
                + getProblems();

    }
}
