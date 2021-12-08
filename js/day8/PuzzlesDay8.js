function puzzle(data)
{
    const segmentCounts = [6, 2, 5, 5, 4, 5, 6, 3, 7, 6]
    const allSignalPatterns = []
    const allOutputValues = []

    let uniqueSegmentCount = 0

    for (const line of data.split(RegExp('\r?\n')))
    {
        const parts = line.split(' | ')
        allSignalPatterns.push(parts[0].split(' '))
        allOutputValues.push(parts[1].split(' '))

        for (const elem of allOutputValues[allOutputValues.length - 1])
        {
            if ((elem.length >= 2 && elem.length <= 4) || elem.length == 7)
                uniqueSegmentCount++
        }
    }

    console.log(uniqueSegmentCount)

    // Part 2 in same function since we have the information here

    
}


const fs = require('fs')

dataText = fs.readFile('day8/data8_1.txt', 'utf8', (err, data) => {
    if (err) {
        console.error(err)
        return
    }

    puzzle(data)
})