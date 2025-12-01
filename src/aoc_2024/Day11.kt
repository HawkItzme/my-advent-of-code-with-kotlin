package aoc_2024

import kotlin.time.measureTimedValue

fun main() {
    val input = readInput("Day11")
    val line = input.first().split(" ").map { string -> string.toLong() }
    val a = measureTimedValue { part1(line) }
    println("Part 1: $a")
    val b = measureTimedValue { part2(line) }
    println("Part 2: $b")
}

private fun part1(line: List<Long>): Int{
    var modifiedLine = line
    repeat(25){
        println(it)
        modifiedLine = blink(modifiedLine)
    }
    return modifiedLine.size
}

private fun part2(line: List<Long>): Long{
    var stoneCounts = line.groupingBy { num -> num }.eachCount()
    var stoneList = NonMaterializedList()
    for ((k,v) in stoneCounts){
        stoneList.add(k.toLong(), v.toLong())
    }
    repeat(75){
        stoneList = blinkGroup(stoneList)
    }
    return stoneList.coll.values.sumOf { integer -> integer.toLong() }
}

fun blinkGroup(stoneList : NonMaterializedList) : NonMaterializedList{
    return buildNonMaterializedList {
        for ((number, count) in stoneList.coll){
            when{
                number == 0L -> {
                    add(1L, count)
                }

                number.toString().length % 2 == 0 -> {
                    val len = number.toString().length
                    val top = number.toString().take(len / 2).toLong()
                    val bot = number.toString().takeLast(len / 2).toLong()
                    add(top, count)
                    add(bot, count)
                }

                else -> {
                    add(number * 2024L, count)
                }
            }
        }
    }
}

data class NonMaterializedList(val coll: MutableMap<Long, Long> = mutableMapOf()){
    fun add(b: Long, counter: Long){
        val curr = coll[b] ?: 0L
        coll[b] = curr + counter
    }
}

fun buildNonMaterializedList(build: NonMaterializedList.() -> Unit) : NonMaterializedList{
    val list = NonMaterializedList()
    list.build()
    return list
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