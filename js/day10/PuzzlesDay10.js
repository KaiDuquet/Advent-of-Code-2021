function puzzle(data)
{
    const lines = data.split(RegExp('\r?\n'))

    const openingBrackets = ['(', '[', '{', '<']
    const closingBrackets = new Map()
    closingBrackets.set(')', [0, 3])
    closingBrackets.set(']', [1, 57])
    closingBrackets.set('}', [2, 1197])
    closingBrackets.set('>', [3, 25137])

    let corruptionScore = 0
    let completionScores = []
    for (const line of lines)
    {
        let corrupted = false
        let completionScore = 0
        let previousOpenBracket = [line.charAt(0)]
        for (let i = 1; i < line.length; i++)
        {
            const index = closingBrackets.get(line.charAt(i))
            if (index == undefined)
                previousOpenBracket .push(line.charAt(i))
            else
            {
                if (previousOpenBracket.pop() != openingBrackets[index[0]])
                {
                    corruptionScore += index[1]
                    corrupted = true
                    break
                }
            }
        }
        if (!corrupted)
        {
            while (previousOpenBracket.length > 0)
            {
                completionScore *= 5
                completionScore += openingBrackets.indexOf(previousOpenBracket.pop()) + 1
            }
            completionScores.push(completionScore)
        }
    }
    completionScores.sort((a, b) => (a - b))
    console.log(corruptionScore)
    console.log(completionScores[Math.floor(completionScores.length / 2)])
}

const fs = require('fs')

dataText = fs.readFile('day10/data10_1.txt', 'utf8', (err, data) => {
    if (err) {
        console.error(err)
        return
    }

    puzzle(data)
})