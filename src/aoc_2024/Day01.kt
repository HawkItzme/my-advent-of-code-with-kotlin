package aoc_2024

import kotlin.math.abs

fun main(){
    val lines = readInput("Day01")
    val (left, right) = lines.map { line ->
        val first = line.substringBefore(" ").toLong()
        val second = line.substringAfterLast(" ").toLong()
        first to second
    }.unzip()

    //PART 1 SOL
    val result =  left.sorted().zip(right.sorted()).map { (first, second) ->
        abs(first - second)
    }.sum()

    println(result)

    //PART 2 SOL
    val freq : Map<Long, Int> = right.groupingBy { it }.eachCount()
    left.fold(0L){ acc, num ->
        acc + num * freq.getOrDefault(num, 0)
    }.also (:: println )

}