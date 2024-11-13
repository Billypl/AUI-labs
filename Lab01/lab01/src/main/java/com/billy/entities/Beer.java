package com.billy.entities;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder(buildMethodName = "buildInternal")
@Data
public class Beer implements Serializable, Comparable<Beer> {

    String name;
    float percentage;
    BeerType type;

    @Override
    public int compareTo(Beer other) {
        if (name.compareTo(other.name) != 0) {
            return name.compareTo(other.name);
        }else if (percentage != other.percentage) {
            return Float.compare(percentage, other.percentage);
        } else {
            return type.compareTo(other.type);
        }
    }

    public static class BeerBuilder {
        public Beer build() {
            Beer beer = this.buildInternal();
            // for beer to automatically be added to its type
            beer.getType().getBeers().add(beer);
            return beer;
        }
    }
}
