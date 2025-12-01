package aoc_2024

fun main(){
    val input = readInput("Day02")

    val lines = input.map { line ->
        line.split(" ").map { str -> str.toInt() }
    }
    part1(report = lines)
    part2(lines)

}

private fun part1(report : List<List<Int>>){
    val safeList = report.count {
        isReportSafe(it)
    }
    println(safeList)
}

private fun isReportSafe(leves : List<Int>) :Boolean{
    val pairs = leves.zipWithNext()
    val allAreIncre : Boolean = pairs.all{pair ->
        pair.second - pair.first in 1..3
    }

    val allAreDecr : Boolean = pairs.all { pair ->
        pair.second - pair.first in -3..-1
    }
    return allAreIncre || allAreDecr
}

private fun part2(report : List<List<Int>>){
    var result = 0
    for (line in report){
        var safe = false
        for (i in 0..line.lastIndex){
            safe = isReportSafe(line.toMutableList().apply { removeAt(i) })
            if (safe) break
        }
        if (safe) result++
    }

    println(result)

}