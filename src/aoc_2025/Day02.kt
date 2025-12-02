package aoc_2025

fun main(){
println(part1(file = "Day02"))
println(part2(file = "Day02"))
}

private fun parse(input : String) : List<Long> = input.split(",").flatMap {
    val ends = it.split("-")
    LongRange(ends.first().toLong(), ends.last().toLong())
}

fun part1(file : String) : Long{
    val parsed = parse(readInputAsString("Day02"))

    val invalidIds = mutableListOf<Long>()

    for (id in parsed){
        val idString = id.toString()
        if (idString.length.rem(2) == 0){
            val half = idString.length / 2
            val firstHalf = idString.take(half)
            val secondHalf = idString.drop(half)
            if (firstHalf == secondHalf) invalidIds.add(id)
        }
    }
    return invalidIds.sum()
}

fun part2(file : String): Long{

    val parsed = parse(readInputAsString("Day02"))

    val invalidIds = mutableListOf<Long>()

    for (id in parsed){
        val idString = id.toString()
        val maxChunkSize = idString.length / 2
        for (chunkSize in 1..maxChunkSize){
            val chunks = idString.chunked(chunkSize)
            val invalid = chunks.all { it == chunks.first() }
            if (invalid){
                invalidIds.add(id)
                break
            }
        }
    }
    return invalidIds.sum()
}


























