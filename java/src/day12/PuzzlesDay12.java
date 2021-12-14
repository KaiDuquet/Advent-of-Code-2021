package day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class PuzzlesDay12
{
    private static HashMap<String, Integer> visitedCaves;
    private static HashMap<String, HashSet<String>> connections;

    private static boolean isSmallCave(String cave)
    {
        int codePoint = cave.codePointAt(0);
        return codePoint >= 97 && codePoint <= 122;
    }

    public static String recursivePath(Stack<String> currentPath, ArrayList<String[]> paths, String doubleVisitedCave)
    {
        String currentCave = currentPath.peek();

        if (currentCave.equals("end"))
        {
            String[] path = new String[currentPath.size()];
            paths.add(currentPath.toArray(path));
            return doubleVisitedCave;
        }

        if (isSmallCave(currentCave))
        {
            Integer visitsLeft = visitedCaves.get(currentCave);
            if (visitsLeft == null) visitsLeft = 1;

            if (visitsLeft == 0)
            {
                if (doubleVisitedCave.isEmpty())
                {
                    doubleVisitedCave = currentCave;
                    visitsLeft = 1;
                }
                else return doubleVisitedCave;
            }
            visitedCaves.put(currentCave, visitsLeft - 1);
        }


        HashSet<String> localConnections = connections.get(currentCave);
        for (String nextCave : localConnections)
        {
            if (nextCave.equals("start")) continue;
            currentPath.push(nextCave);
            doubleVisitedCave = recursivePath(currentPath, paths, doubleVisitedCave);
            currentPath.pop();
        }


        Integer visitsLeft = visitedCaves.get(currentCave);

        if (visitsLeft != null)
        {
            if (currentCave.equals(doubleVisitedCave))
            {
                doubleVisitedCave = "";
                visitedCaves.put(currentCave, 0);
            }
            else visitedCaves.remove(currentCave);
        }
        return doubleVisitedCave;
    }

    public static void main(String[] args)
    {
        visitedCaves = new HashMap<>();
        connections = new HashMap<>();

        List<String> lines = Collections.emptyList();
        try
        {
            lines = Files.readAllLines(Paths.get("src/day12/data12_1.txt"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        for (String line : lines)
        {
            String[] caves = line.split("-");
            String firstCave = caves[0];
            String secondCave = caves[1];

            if (connections.containsKey(firstCave))
                connections.get(firstCave).add(secondCave);
            else
            {
                HashSet<String> newSet = new HashSet<>();
                newSet.add(secondCave);
                connections.put(firstCave, newSet);
            }

            if (connections.containsKey(secondCave))
                connections.get(secondCave).add(firstCave);
            else
            {
                HashSet<String> newSet = new HashSet<>();
                newSet.add(firstCave);
                connections.put(secondCave, newSet);
            }
        }

        ArrayList<String[]> paths = new ArrayList<>();
        Stack<String> currentPath = new Stack<>();

        currentPath.push("start");

        recursivePath(currentPath, paths, "");

        for (String[] path : paths)
        {
            for (String cave : path)
            {
                System.out.print(cave + ",");
            }
            System.out.println();
        }
        System.out.println("\nTotal Path Count: " + paths.size());
    }
}
