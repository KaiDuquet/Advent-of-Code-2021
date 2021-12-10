function intersect(s1, s2)
{
    var t;
    if (s2.length > s1.length) t = s2, s2 = s1, s1 = t
    return s1.split('').filter(function(e)
    {
        return s2.indexOf(e) > - 1
    }).filter(function (e, i, c)
    {
        return c.indexOf(e) === i
    }).join('')
}

function difference(s1, s2)
{
    var t;
    if (s2.length > s1.length) t = s2, s2 = s1, s1 = t
    for (let i = 0; i < s2.length; i++)
        s1 = s1.replace(s2.charAt(i), '')
    return s1
}


function puzzle(data)
{
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

    let totalSum = 0
    for (let i = 0; i < allSignalPatterns.length; i++)
    {
        const possibleSegmentConfigs = ['abcdefg', 'abcdefg', 'abcdefg', 'abcdefg', 'abcdefg', 'abcdefg', 'abcdefg']
        const solvedSegmentsOfDigit = ['', '', '', '', '', '', '', '', '', '']

        let currentSignalPatterns = allSignalPatterns[i]
        currentSignalPatterns.sort((a, b) => {return a.length - b.length})

        solvedSegmentsOfDigit[1] = currentSignalPatterns[0]
        solvedSegmentsOfDigit[7] = currentSignalPatterns[1]
        solvedSegmentsOfDigit[4] = currentSignalPatterns[2]
        solvedSegmentsOfDigit[8] = currentSignalPatterns[9]

        possibleSegmentConfigs[2] = solvedSegmentsOfDigit[1]
        possibleSegmentConfigs[5] = solvedSegmentsOfDigit[1]

        possibleSegmentConfigs[0] = difference(solvedSegmentsOfDigit[7], possibleSegmentConfigs[2])

        possibleSegmentConfigs[1] = difference(solvedSegmentsOfDigit[4], possibleSegmentConfigs[2])
        possibleSegmentConfigs[3] = possibleSegmentConfigs[1]

        possibleSegmentConfigs[4] = difference(possibleSegmentConfigs[4], possibleSegmentConfigs[1] + possibleSegmentConfigs[0] + possibleSegmentConfigs[2])
        possibleSegmentConfigs[6] = possibleSegmentConfigs[4]

        for (let j = 3; j < 9; j++)
        {
            if (j >= 3 && j <= 5)
            {
                if (allSignalPatterns[i][j].includes(possibleSegmentConfigs[1].charAt(0)) && allSignalPatterns[i][j].includes(possibleSegmentConfigs[1].charAt(1)))
                {
                    solvedSegmentsOfDigit[5] = allSignalPatterns[i][j]

                    /*solvedSegmentsOfDigit[5] = allSignalPatterns[i][j]

                    const missingSegments = difference('abcdefg', solvedSegmentsOfDigit[5])
                    
                    possibleSegmentConfigs[2] = intersect(possibleSegmentConfigs[2], missingSegments)
                    possibleSegmentConfigs[5] = difference(possibleSegmentConfigs[5], possibleSegmentConfigs[2])
                    possibleSegmentConfigs[4] = difference(missingSegments, possibleSegmentConfigs[2])
                    possibleSegmentConfigs[6] = difference(possibleSegmentConfigs[6], possibleSegmentConfigs[4])*/
                }
                else if (allSignalPatterns[i][j].includes(possibleSegmentConfigs[2].charAt(0)) && allSignalPatterns[i][j].includes(possibleSegmentConfigs[2].charAt(1)))
                {
                    solvedSegmentsOfDigit[3] = allSignalPatterns[i][j]
                }
                else
                    solvedSegmentsOfDigit[2] = allSignalPatterns[i][j]
            }
            else
            {
                const missingSegment = difference('abcdefg', allSignalPatterns[i][j])
                if (missingSegment == possibleSegmentConfigs[3].charAt(0) || missingSegment == possibleSegmentConfigs[3].charAt(1))
                    solvedSegmentsOfDigit[0] = allSignalPatterns[i][j]
                else if (missingSegment == possibleSegmentConfigs[2].charAt(0) || missingSegment == possibleSegmentConfigs[2].charAt(1))
                    solvedSegmentsOfDigit[6] = allSignalPatterns[i][j]
                else
                    solvedSegmentsOfDigit[9] = allSignalPatterns[i][j]
            }
        }

        let value = ''
        for (const outputValue of allOutputValues[i])
        {
            for (let digitIndex = 0; digitIndex < 10; digitIndex++)
            {
                if (difference(outputValue, solvedSegmentsOfDigit[digitIndex]) == '')
                {
                    value += digitIndex + ''
                    break
                }
            }
        }
        console.log(value)
        totalSum += Number(value)
    }
    console.log(totalSum)
}


const fs = require('fs')

dataText = fs.readFile('day8/data8_1.txt', 'utf8', (err, data) => {
    if (err) {
        console.error(err)
        return
    }

    puzzle(data)
})