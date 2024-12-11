fun main(){
    val input = readInput("Day02")

    val lines = input.map { line ->
        line.split(" ").map { str -> str.toInt() }
    }
    part1(report = lines)

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