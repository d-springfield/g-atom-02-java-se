package stream;

import org.w3c.dom.ls.LSOutput;

import javax.crypto.spec.PSource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Developer dev1 = new Developer("Наташа", Arrays.asList("Java", "C++"));
        Developer dev2 = new Developer("Эрнест", Arrays.asList("Java", "Python"));
        Developer dev3 = new Developer("Элла", Arrays.asList("С#", "Python", "JavaScript"));
        Stream<Developer> developerStream = Stream.of(dev1, dev2, dev3);

        solve1(Arrays.asList(dev1, dev2, dev3));
        solve2(Arrays.asList(dev1, dev2, dev3));
    }

    private static void solve1(List<Developer> developers) {
        System.out.println("Solution 1:");

        Map<String, Integer> languagesMap = developers.stream()
                .flatMap(developer -> developer.getLanguages().stream())
                .collect(Collectors.toMap(
                        Function.identity(),
                        value -> 1,
                        Integer::sum
                ));

        Set<String> uniqLanguages = languagesMap.entrySet()
                .stream()
                .filter(e -> e.getValue() < 2)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());


        List<String> uniqLanguageDevelopers = developers.stream()
                .filter(developer -> developer.getLanguages()
                        .stream()
                        .anyMatch(uniqLanguages::contains)
                )
                .map(Developer::getName)
                .collect(Collectors.toList());

        System.out.println(uniqLanguageDevelopers);
    }

    private static void solve2(List<Developer> developers) {
        System.out.println("Solution 2");
        System.out.println("Not implemented");
    }
}
