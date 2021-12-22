package day14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class PuzzlesDay14
{

    private static void safeIncrement(HashMap<String, Long> counts, String key, long increment)
    {
        long count = 0;
        if (counts.containsKey(key))
            count = counts.get(key);
        counts.put(key, count + increment);
    }

    public static void puzzle1(HashMap<String, Long> polymerPairs, HashMap<String, Long> elementCounts, HashMap<String, String> rules)
    {
        int steps = 40;

        HashMap<String, Long> auxilary = new HashMap<>();

        for (int i = 0; i < steps; i++)
        {
            for (var pair : polymerPairs.entrySet())
            {
                String addedElement = rules.get(pair.getKey());
                String[] separatedPair = pair.getKey().split("");

                safeIncrement(elementCounts, addedElement, pair.getValue());
                safeIncrement(auxilary, separatedPair[0] + addedElement, pair.getValue());
                safeIncrement(auxilary, addedElement + separatedPair[1], pair.getValue());
                safeIncrement(polymerPairs, pair.getKey(), -pair.getValue());
            }

            for (var pair : auxilary.entrySet())
            {
                safeIncrement(polymerPairs, pair.getKey(), pair.getValue());
                auxilary.put(pair.getKey(), 0L);
            }
        }

        System.out.println(polymerPairs);

        long maxValue = -1;
        long minValue = -1;

        for (String e : elementCounts.keySet())
        {
            long count = elementCounts.get(e);
            if (maxValue == -1)
            {
                maxValue = count;
                minValue = count;
                continue;
            }
            if (count > maxValue)
                maxValue = count;
            if (count < minValue)
                minValue = count;
        }
        System.out.println(maxValue - minValue);
    }

    public static void main(String[] args)
    {
        List<String> lines = Collections.emptyList();
        try
        {
            lines = Files.readAllLines(Paths.get("src/day14/data14_1.txt"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        String polymer = lines.remove(0);
        HashMap<String, String> insertionRules = new HashMap<>();
        HashMap<String, Long> polymerPairs = new HashMap<>();
        HashMap<String, Long> elementCounts = new HashMap<>();

        for (String line : lines)
        {
            if (line.isEmpty()) continue;

            String[] pair = line.split(" -> ");
            insertionRules.put(pair[0], pair[1]);
        }

        for (int i = 0; i < polymer.length() - 1; i++)
        {
            safeIncrement(polymerPairs, polymer.substring(i, i + 2), 1);
            safeIncrement(elementCounts, polymer.substring(i, i + 1), 1);
        }
        safeIncrement(elementCounts, polymer.substring(polymer.length() - 1), 1);

        puzzle1(polymerPairs, elementCounts, insertionRules);

    }
}
