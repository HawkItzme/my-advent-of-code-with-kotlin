import kotlin.time.measureTimedValue

fun main() {
    val input = readInput("Day11")
    val line = input.first().split(" ").map { string -> string.toLong() }
    val a = measureTimedValue { part1(line) }
    println("Part 1: $a")
}

private fun part1(line: List<Long>): Int{
    var modifiedLine = line
    repeat(25){
        println(it)
        modifiedLine = blink(modifiedLine)
    }
    return modifiedLine.size
}

fun blink(stones: List<Long>): List<Long>{
    return buildList<Long> {
        for (stone in stones){
            when{
                stone == 0L -> {
                    add(1L)
                }

                stone.toString().length % 2 == 0 -> {
                    val len = stone.toString().length
                    add(stone.toString().take(len/2).toLong())
                    add(stone.toString().takeLast(len/2).toLong())
                }

                else -> {
                    add(stone * 2024L)
                }
            }
        }
    }
}