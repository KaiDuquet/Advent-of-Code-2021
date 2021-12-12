const grid = []
const basins = []

function puzzle(data)
{
    data = data.split(RegExp('\r?\n'))
    for (let i = 0; i < data.length; i++)
    {
        let row = data[i].split('').map(Number)
        grid.push(row)
    }

    let sum = 0;
    let left, right, up, down;
    for (let i = 0; i < grid.length; i++)
    {
        for (let j = 0; j < grid[0].length; j++)
        {
            const num = grid[i][j]
            if (num == 9)
                continue

            left = 1000;
            right = 1000;
            up = 1000;
            down = 1000;
            if (j - 1 >= 0)
                left = grid[i][j - 1]
            if (j + 1 < grid[0].length)
                right = grid[i][j + 1]
            if (i - 1 >= 0)
                up = grid[i - 1][j]
            if (i + 1 < grid.length)
                down = grid[i + 1][j]

            
            if (num < left && num < right && num < up && num < down)
            {
                sum += num + 1
                basins.push([i, j])
            }
        }
    }
    console.log(sum)

    puzzle2()
}

function puzzle2()
{
    const lowPointCount = basins.length
    const sizes = []

    for (let i = 0; i < lowPointCount; i++)
    {
        const coords = basins.shift()
        sizes.push(basinRecursive(coords[0], coords[1], 0))
    }
    sizes.sort((a, b) => b - a)
    console.log(sizes[0] * sizes[1] * sizes[2])
}

function basinRecursive(row, col, count)
{
    if (grid[row][col] == 9)
        return count

    grid[row][col] = 9
    count += 1

    if (row - 1 >= 0)
        count = basinRecursive(row - 1, col, count)
    if (col + 1 < grid[0].length)
        count = basinRecursive(row, col + 1, count)
    if (row + 1 < grid.length)
        count = basinRecursive(row + 1, col, count)
    if (col - 1 >= 0)
        count = basinRecursive(row, col - 1, count)

    return count
}

const fs = require('fs')

dataText = fs.readFile('day9/data9_1.txt', 'utf8', (err, data) => {
    if (err) {
        console.error(err)
        return
    }

    puzzle(data)
})