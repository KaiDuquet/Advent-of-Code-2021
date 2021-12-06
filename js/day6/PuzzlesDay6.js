function puzzle(dataText, targetDay)
{
    const fishCounts = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
    var lanternfish = dataText.split(",")

    for (const fish of lanternfish)
    {
        fishCounts[Number(fish)] += 1
    }

    let day = 0
    while (day < targetDay)
    {
        for (let i = 0; i < fishCounts.length; i++)
        {
            if (i == 0)
            {
                fishCounts[7] += fishCounts[0]
                fishCounts[9] += fishCounts[0]
                fishCounts[0] = 0
            }
            else
            {
                const transFish = fishCounts[i]
                fishCounts[i] = 0
                fishCounts[i - 1] = transFish
            }
        }
        day++
    }
    
    let fishSum = 0
    for (const fish of fishCounts)
    {
        fishSum += fish
    }

    console.log(fishSum)
}


const fs = require('fs')

dataText = fs.readFile('day6/data6_1.txt', 'utf8', (err, data) => {
    if (err) {
        console.error(err)
        return
    }
    puzzle(data, 256)
})