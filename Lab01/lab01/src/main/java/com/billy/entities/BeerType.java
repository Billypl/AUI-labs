package com.billy.entities;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class BeerType implements Serializable, Comparable<BeerType> {
    String name;
    boolean isAlcoholFree;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    List<Beer> beers = new ArrayList<>();

    @Override
    public int compareTo(BeerType other) {
        if (name.compareTo(other.getName()) != 0) {
            return name.compareTo(other.getName());
        } else if (isAlcoholFree != other.isAlcoholFree()) {
            return -1;
        } else {
            return 0;
        }
    }
}
