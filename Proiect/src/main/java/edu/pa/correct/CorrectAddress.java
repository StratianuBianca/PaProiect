package edu.pa.correct;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CorrectAddress {
    private final String country;
    private final String state;
    private final String city;
}
