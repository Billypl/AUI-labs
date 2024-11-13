package com.billy;

import com.billy.dtos.BeerDto;
import com.billy.entities.Beer;
import com.billy.entities.BeerType;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        System.out.println("Exercise 2\n");
        Map<String, BeerType> beerGenresByName = Stream.of(
                BeerType.builder()
                        .name("Craft")
                        .isAlcoholFree(false)
                        .build(),
                BeerType.builder()
                        .name("Flavoured")
                        .isAlcoholFree(true)
                        .build(),
                BeerType.builder()
                        .name("Normal")
                        .isAlcoholFree(false)
                        .build()
        ).collect(Collectors.toMap(BeerType::getName, x -> x));
        List<BeerType> allBeerTypes = beerGenresByName.values().stream().toList();

        List<Beer> beers = List.of(
                Beer.builder()
                        .name("Specjal")
                        .percentage(6f)
                        .type(beerGenresByName.get("Craft"))
                        .build(),
                Beer.builder()
                        .name("Somersby")
                        .percentage(0.0f)
                        .type(beerGenresByName.get("Flavoured"))
                        .build(),
                Beer.builder()
                        .name("Harnaś")
                        .percentage(5.8f)
                        .type(beerGenresByName.get("Normal"))
                        .build(),
                Beer.builder()
                        .name("Romper")
                        .percentage(12.0f)
                        .type(beerGenresByName.get("Normal"))
                        .build()
        );

        allBeerTypes.forEach(beerType -> {
            System.out.printf("%s:\n", beerType.getName());
            beerType.getBeers().forEach(beer ->
                System.out.printf("\t%s\n", beer.toString())
            );
        });

        System.out.println("\nExercise 3\n");
        Set<Beer> allBeers = allBeerTypes.stream()
                .flatMap(x -> x.getBeers().stream())
                .collect(Collectors.toSet());
        allBeers.forEach(System.out::println);

        System.out.println("\nExercise 4\n");
        allBeers.stream()
                .filter(x -> x.getPercentage() < 12f)
                .sorted(Comparator.comparing(x -> x.getType().getName()))
                .forEach(System.out::println);

        System.out.println("\nExercise 5\n");
        List<BeerDto> allBeersDtos = allBeers.stream()
                .map(x -> BeerDto.builder()
                        .name(x.getName())
                        .percentage(x.getPercentage())
                        .genre(x.getType().getName())
                        .build()
                )
                .sorted()
                .toList();
        allBeersDtos.forEach(System.out::println);

        System.out.println("\nExercise 6\n");
        // Save to file
        try (
                var fos = new FileOutputStream("data.bin");
                var oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(allBeerTypes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Read from file
        try (
                var fis = new FileInputStream("data.bin");
                var ois = new ObjectInputStream(fis);
        ) {
            /* // alternative, weird way
            ((List<BeerType>) ois.readObject())
                    .forEach(beerType -> {
                        System.out.printf("%s:\n", beerType.getName());
                        beerType.getBeers().forEach(beer -> System.out.printf("%s\n", beer.toString()));
                    });
            */
            for (BeerType beerType : ((List<BeerType>) ois.readObject())) {
                System.out.printf("%s:\n", beerType.getName());
                beerType.getBeers().forEach(beer -> System.out.printf("%s\n", beer.toString()));
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\nExercise 7\n");
        try (var pool = new ForkJoinPool(4)) {
            pool.submit(() -> allBeers.stream().toList().parallelStream()
                .forEach(beer -> {
                    try {
                        Thread.sleep((int) beer.getPercentage() * 100L);
                        System.out.println(beer);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })).join();
        }
    }
}
