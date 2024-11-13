package com.billy.dtos;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class BeerDto implements Comparable<BeerDto> {
    String name;
    float percentage;
    String genre;

    @Override
    public int compareTo(BeerDto other) {
        if (this.name.compareTo(other.name) != 0) {
            return this.name.compareTo(other.name);
        } else if (this.genre.compareTo(other.genre) != 0) {
            return this.genre.compareTo(other.genre);
        } else {
            return Float.compare(this.percentage, other.percentage);
        }
    }
}
