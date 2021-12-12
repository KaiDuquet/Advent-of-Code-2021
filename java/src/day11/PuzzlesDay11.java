package day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

class Octopus
{
    public int x, y;
    public int e;
    public boolean flashing;
    public Octopus(int x, int y, int e, boolean flashing)
    {
        this.x = x;
        this.y = y;
        this.e = e;
        this.flashing = flashing;
    }
}

public class PuzzlesDay11
{
    private static Octopus[][] octopuses = new Octopus[10][10];

    private static int energize(Octopus o, Stack<Octopus> flashStack)
    {
        return energize(o, flashStack, false);
    }

    private static int energize(Octopus o, Stack<Octopus> flashStack, boolean incrementPass)
    {
        if (o.flashing)
        {
            if (incrementPass)
                o.flashing = false;
            else return 0;
        }

        o.e++;
        if (o.e > 9)
        {
            o.e = 0;
            o.flashing = true;
            flashStack.push(o);
            return 1;
        }
        return 0;
    }

    public static void puzzle1(int steps)
    {
        int totalFlashes = 0;

        for (int i = 0; i < steps; i++)
        {
            int flashes = 0;
            Stack<Octopus> flashStack = new Stack<>();

            for (int y = 0; y < 10; y++)
            {
                for (int x = 0; x < 10; x++)
                {
                    flashes += energize(octopuses[y][x], flashStack, true);
                }
            }

            while (!flashStack.empty())
            {
                Octopus o = flashStack.pop();
                if (o.x - 1 >= 0)
                {
                    flashes += energize(octopuses[o.x - 1][o.y], flashStack);
                    if (o.y - 1 >= 0) flashes += energize(octopuses[o.x - 1][o.y - 1], flashStack);
                    if (o.y + 1 < 10) flashes += energize(octopuses[o.x - 1][o.y + 1], flashStack);
                }
                if (o.x + 1 < 10)
                {
                    flashes += energize(octopuses[o.x + 1][o.y], flashStack);
                    if (o.y - 1 >= 0) flashes += energize(octopuses[o.x + 1][o.y - 1], flashStack);
                    if (o.y + 1 < 10) flashes += energize(octopuses[o.x + 1][o.y + 1], flashStack);
                }
                if (o.y - 1 >= 0) flashes += energize(octopuses[o.x][o.y - 1], flashStack);
                if (o.y + 1 < 10) flashes += energize(octopuses[o.x][o.y + 1], flashStack);
            }

            if (flashes == 100)
            {
                System.out.println("ALL FLASHED AT " + (i + 1));
                printOctopuses();
                break;
            }
            totalFlashes += flashes;
        }

        System.out.println(totalFlashes);
    }

    private static void printOctopuses()
    {
        for (int y = 0; y < 10; y++)
        {
            for (int x = 0; x < 10; x++)
            {
                System.out.print(octopuses[y][x].e);
            }
            System.out.println();
        }
        System.out.println("\n----------\n");
    }

    public static void main(String[] args)
    {
        List<String> lines = Collections.emptyList();
        try
        {
            lines = Files.readAllLines(Paths.get("src/day11/data11_1.txt"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        for (int y = 0; y < 10; y++)
        {
            String[] line = lines.get(y).split("");
            for (int x = 0; x < 10; x++)
            {
                octopuses[y][x] = new Octopus(y, x, Integer.parseInt(line[x]), false);
                System.out.print(octopuses[y][x].e);
            }
            System.out.println();
        }
        System.out.println("\n----------\n");

        puzzle1(1000);
    }
}
