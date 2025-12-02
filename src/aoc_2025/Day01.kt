package aoc_2025

fun main(){
    println(part1(readInputAsListOfString("Day01")))
    println(part2(readInputAsListOfString("Day01")))

}

fun part1(input : List<String>):Int{
    var dial = 50
    var zeroes = 0
    input.forEach {
        if(it[0] == 'L'){
            dial = (dial - it.drop(1).toInt()) % (100)
        }else{
            dial = (dial + it.drop(1).toInt()) % 100
        }
        if(dial == 0) zeroes++
    }
    return zeroes
}

fun part2(input : List<String>):Int{
    var dial = 50
    var zeroes = 0
    input.forEach {
        val delta = if(it[0] == 'L') -1 else 1

        repeat(it.drop(1).toInt()) {
            dial = (dial + delta) % 100
            if (dial == 0) zeroes++
        }
    }
    return zeroes
}