package day13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class PuzzlesDay13
{
    private static HashMap<Integer, HashMap<Integer, Integer>> paperGrid = new HashMap<>();
    private static Deque<String[]> foldInstructions = new ArrayDeque<>();

    private static int maxXvalue;
    private static int maxYvalue;

    private static void puzzle1()
    {
        for (String[] instruction : foldInstructions)
        {
            int foldIndex = Integer.parseInt(instruction[1]);

            if (instruction[0].equals("y"))
            {
                List<Integer> foldableRows = paperGrid.keySet()
                        .stream()
                        .filter(key -> key > foldIndex)
                        .collect(Collectors.toList());

                for (int rowIndex : foldableRows)
                {
                    int newY = 2 * foldIndex - rowIndex;

                    if (!paperGrid.containsKey(newY))
                        paperGrid.put(newY, new HashMap<>());

                    HashMap<Integer, Integer> row = paperGrid.get(rowIndex);
                    HashMap<Integer, Integer> newRow = paperGrid.get(newY);

                    for (Integer pointX : row.keySet())
                    {
                        int newCount = row.get(pointX);
                        if (newRow.containsKey(pointX))
                            newCount += newRow.get(pointX);
                        newRow.put(pointX, newCount);
                    }
                    paperGrid.remove(rowIndex, row);
                }
            }
            else
            {
                for (Integer rowIndex : paperGrid.keySet())
                {
                    HashMap<Integer, Integer> row = paperGrid.get(rowIndex);
                    List<Integer> foldableKeys = row.keySet()
                            .stream()
                            .filter(key -> key > foldIndex)
                            .collect(Collectors.toList());

                    for (int colIndex : foldableKeys)
                    {
                        int newX = 2 * foldIndex - colIndex;

                        int newCount = row.get(colIndex);
                        if (row.containsKey(newX))
                            newCount += row.get(newX);
                        row.put(newX, newCount);
                        row.remove(colIndex);
                    }
                }
            }
        }
        System.out.println(paperGrid.toString());
        System.out.println(getGridCount());
    }

    private static int getGridCount()
    {
        int totalSize = 0;
        for (Integer key : paperGrid.keySet())
        {
            totalSize += paperGrid.get(key).size();
        }
        return totalSize;
    }

    private static void puzzle2()
    {
        StringBuilder builder = new StringBuilder();
        for (Integer row : paperGrid.keySet())
        {
            StringBuilder rowBuild = new StringBuilder();
            for (Integer col : paperGrid.get(row).keySet())
            {
                if (col + 1 > rowBuild.length())
                    rowBuild.append(".".repeat(Math.max(0, col - rowBuild.length() + 1)));
                rowBuild.replace(col, col + 1, "#");
            }
            builder.append(rowBuild).append('\n');
        }
        System.out.println(builder.toString());
    }

    public static void main(String[] args)
    {
        List<String> lines = Collections.emptyList();
        try
        {
            lines = Files.readAllLines(Paths.get("src/day13/data13_1.txt"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        for (String line : lines)
        {
            if (line.isEmpty()) continue;
            if (Character.isDigit(line.charAt(0)))
            {
                String[] coords = line.split(",");
                int x = Integer.parseInt(coords[0]);
                int y = Integer.parseInt(coords[1]);

                if (x > maxXvalue) maxXvalue = x;
                if (y > maxYvalue) maxYvalue = y;

                if (paperGrid.containsKey(y))
                    paperGrid.get(y).put(x, 1);
                else
                {
                    HashMap<Integer, Integer> row = new HashMap<>();
                    row.put(x, 1);
                    paperGrid.put(y, row);
                }
            }
            else
                foldInstructions.add(line.substring(11).split("="));
        }
        puzzle1();
        puzzle2();
    }
}
