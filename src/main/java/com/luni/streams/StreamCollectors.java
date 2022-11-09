package com.luni.streams;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;

import com.luni.streams.domain.DS;
import com.luni.streams.domain.Dish;
import com.luni.streams.domain.Dish.Type;

public class StreamCollectors {
    public enum CaloricLevel {
        DIET, NORMAL, FAT
    };

    public static void execute() {
        StreamCollectors collectors = new StreamCollectors();
        // collectors.printMaxCalorieDish();
        // collectors.printAverageDishCalories();
        // collectors.printCalorieStatistics();
        // collectors.printDishGroupedByType();
        // collectors.printDishGroupedAndFilteredByType();
        // collectors.printDishGroupedFilteredAndMappedByType();
        // collectors.printDishGroupedByCalorieLevel();
        collectors.printGroupedAndFlatMapped();
    }

    private void printMaxCalorieDish() {
        Optional<Dish> maxCaloriDish = DS.getDishes().stream().collect(maxBy(comparing(Dish::getCalories)));
        System.out.println("Max Calorie Dish: " + maxCaloriDish.get());
    }

    private void printAverageDishCalories() {
        double averageCalories = DS.getDishes().stream().collect(Collectors.averagingInt(Dish::getCalories));
        System.out.println("Average Calories: " + averageCalories);
    }

    private void printCalorieStatistics() {
        IntSummaryStatistics statistics = DS.getDishes().stream().collect(Collectors.summarizingInt(Dish::getCalories));
        System.out.println("Calorie Statistics: " + statistics);
    }

    private void printDishGroupedByType() {
        Map<Type, List<Dish>> dishes = DS.getDishes().stream().collect(groupingBy(Dish::getType));

        dishes.forEach((k, v) -> {
            System.out.println("==========================================================================");
            System.out.println("Key: " + k + " | Value: " + v);
        });
    }

    private void printDishGroupedAndFilteredByType() {
        System.out.println("\n Grouped and Filtered");

        Map<Type, List<Dish>> dishes = DS.getDishes().stream()
                .collect(groupingBy(Dish::getType, filtering(x -> x.getCalories() > 500, Collectors.toList())));

        dishes.forEach((k, v) -> {
            System.out.println("==========================================================================");
            System.out.println("Key: " + k + " | Value: " + v);
        });
    }

    private void printDishGroupedFilteredAndMappedByType() {
        System.out.println("\n Grouped Filtered and Mapped to String");
        Map<Type, List<String>> dishes = DS.getDishes().stream()
                .collect(groupingBy(Dish::getType, mapping(Dish::getName, Collectors.toList())));

        dishes.forEach((k, v) -> {
            System.out.println("==========================================================================");
            System.out.println("Key: " + k + " | Value: " + v);
        });
    }

    private void printDishGroupedByCalorieLevel() {
        Map<CaloricLevel, List<Dish>> dishes = DS.getDishes().stream().collect(
                groupingBy(d -> {
                    if (d.getCalories() <= 400)
                        return CaloricLevel.DIET;
                    else if (d.getCalories() <= 700)
                        return CaloricLevel.NORMAL;
                    else
                        return CaloricLevel.FAT;
                }));

        System.out.println("-----------------------------------------------------------------------");
        System.out.println("-----------------   Group By Caloric Levels   --------------------------");
        System.out.println("-----------------------------------------------------------------------");
        dishes.forEach((k, v) -> {
            System.out.println("==========================================================================");
            System.out.println("Key: " + k + " | Value: " + v);
        });
    }

    private void printGroupedAndFlatMapped() {
        System.out.println("\n\n\n\n");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("---------------------Dishes With Tags----------------------------------");
        System.out.println("-----------------------------------------------------------------------");

        Map<String, List<String>> dishTags = new HashMap<>();
        dishTags.put("kibab", asList("greasy", "salty"));
        dishTags.put("beef", asList("salty", "roasted"));
        dishTags.put("chicken", asList("fried", "crisp"));
        dishTags.put("french fries", asList("greasy", "fried"));
        dishTags.put("rice", asList("light", "natural"));
        dishTags.put("season fruit", asList("fresh", "natural"));
        dishTags.put("pizza", asList("tasty", "salty"));
        dishTags.put("prawns", asList("tasty", "roasted"));
        dishTags.put("salmon", asList("delicious", "fresh"));

        Map<Type, List<String>> dishesWithTags = DS.getDishes().stream().collect(groupingBy(Dish::getType,
                flatMapping(dish -> dishTags.get(dish.getName()).stream(), toList())));

        dishesWithTags.forEach((k, v) -> {
            System.out.println("==========================================================================");
            System.out.println("Key: " + k + " | Value: " + v);
        });
    }

}
