/*function countingSort(data)
{
    let maxValue = 0;
    
    for (const integer of data)
    {
        if (integer > maxValue)
        {
            maxValue = integer;
        }
    }


}*/

function puzzle1(positions)
{
    positions.sort((a, b) => {return a - b})
    
    var median = positions[Math.floor(positions.length / 2)]
    console.log(median)

    let fuelCost = 0
    for (const position of positions)
    {
        fuelCost += Math.abs(position - median)
    }
    console.log(fuelCost)
}

function puzzle2(positions)
{
    let average = 0
    for (const position of positions)
    {
        average += position
    }
    average = Math.round(average / positions.length)

    console.log(average)

    let fuelCost = 0
    for (const position of positions)
    {
        let diff = Math.abs(position - average)
        fuelCost += (diff * (diff + 1)) / 2
    }
    console.log(fuelCost)
}

// Boring O(n*m) algorithm, will try to figure out some better algo for this shit, there has to be something elegant
function temp(positions)
{
    let mavValue = 0
    for (const pos of positions)
    {
        if (pos > mavValue)
        {
            mavValue = pos
        }
    }

    let minFuelCount = 0
    let minI = 0
    for (var i = 0; i < mavValue; i++)
    {
        let fuelCount = 0
        for (const pos of positions)
        {
            let diff = Math.abs(pos - i)
            fuelCount += (diff * (diff + 1)) / 2
        }
        if (fuelCount < minFuelCount || minFuelCount == 0)
        {
            minFuelCount = fuelCount
            minI = i
        }
    }

    console.log(minFuelCount)
    console.log(minI)
}

const fs = require('fs')

dataText = fs.readFile('day7/data7_1.txt', 'utf8', (err, data) => {
    if (err) {
        console.error(err)
        return
    }

    data = data.split(',').map(Number)
    puzzle1(data)
    temp(data)
    //puzzle2(data)
})