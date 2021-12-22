package day15;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class MinHeap
{
    private ArrayList<Node> heap = new ArrayList<>();

    void add(Node element)
    {
        heap.add(element);
        heapifyUp();
    }

    Node get()
    {
        swap(0, heap.size() - 1);
        Node e = heap.remove(heap.size() - 1);
        heapifyDown();
        return e;
    }

    boolean isEmpty()
    {
        return heap.isEmpty();
    }

    private int parentOf(int index)
    {
        int parentIndex = Math.floorDiv(index - 1, 2);

        return parentIndex >= 0 ? parentIndex : -1;
    }

    private int priorityChildOf(int index)
    {
        if (2 * index + 1 >= heap.size())
            return -1;

        Node leftChild = heap.get(2 * index + 1);
        if (2 * index + 2 >= heap.size() || leftChild.dist < heap.get(2 * index + 2).dist)
            return 2 * index + 1;
        return 2 * index + 2;
    }

    private void swap(int index1, int index2)
    {
        Node temp = heap.get(index1);
        heap.set(index1, heap.get(index2));
        heap.set(index2, temp);
    }

    private void heapifyUp()
    {
        int index = heap.size() - 1;
        int parent = parentOf(index);
        while (parent != -1)
        {
            if (heap.get(index).dist < heap.get(parent).dist)
            {
                swap(index, parent);
                index = parent;
            }
            else break;
            parent = parentOf(index);
        }
    }

    private void heapifyDown()
    {
        int index = 0;
        int child = priorityChildOf(index);
        while (child != -1)
        {
            if (heap.get(index).dist > heap.get(child).dist)
            {
                swap(index, child);
                index = child;
            }
            else break;
            child = priorityChildOf(index);
        }
    }

}

class Node
{
    int row, col;
    int risk;
    int dist;

    @Override
    public String toString()
    {
        return "[" + risk + ", " + dist + "]";
    }
}

public class PuzzlesDay15
{

    public static void puzzle1(Node[][] grid)
    {
        int maxRow = grid.length;
        int maxCol = grid[0].length;

        MinHeap heap = new MinHeap();

        grid[0][0].dist = 0;
        heap.add(grid[0][0]);

        while (!heap.isEmpty())
        {
            Node vertex = heap.get();
            Node[] adjacents = {null, null, null, null};
            int row = vertex.row;
            int col = vertex.col;

            if (col + 1 < maxCol) adjacents[0] = grid[row][col + 1];
            if (row + 1 < maxRow) adjacents[1] = grid[row + 1][col];
            if (col - 1 >= 0) adjacents[2] = grid[row][col - 1];
            if (row - 1 >= 0) adjacents[3] = grid[row - 1][col];

            for (Node u : adjacents)
            {
                if (u == null) continue;

                if (u.dist > vertex.dist + u.risk)
                {
                    u.dist = vertex.dist + u.risk;
                    heap.add(u);
                }
            }
            if (row == maxRow - 1 && col == maxCol - 1)
            {
                System.out.println(vertex.dist);
                break;
            }
        }
    }

    public static void main(String[] args)
    {
        List<String> lines = Collections.emptyList();
        try
        {
            lines = Files.readAllLines(Paths.get("src/day15/data15_1.txt"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        Node[][] grid = new Node[lines.size() * 5][];

        for (int rowOffset = 0; rowOffset < 5; rowOffset++)
        {
            for (int row = 0; row < lines.size(); row++)
            {
                grid[row + rowOffset * lines.size()] = new Node[lines.get(0).length() * 5];
                for (int colOffset = 0; colOffset < 5; colOffset++)
                {
                    for (int col = 0; col < lines.get(0).length(); col++)
                    {
                        Node node = new Node();
                        node.row = row + rowOffset * lines.size();
                        node.col = col + colOffset * lines.get(0).length();
                        node.risk = Character.getNumericValue(lines.get(row).charAt(col)) + rowOffset + colOffset;
                        if (node.risk > 9)
                            node.risk -= 9;
                        node.dist = 1000000;
                        grid[node.row][node.col] = node;
                    }
                }
            }
        }

        puzzle1(grid);
    }
}
