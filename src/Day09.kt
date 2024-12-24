fun main() {
    val input = readInput("Day09")
    val line = input.first()
    val list = mutableListOf<Segment>()
    var cnt = 0
    for ((idx, char) in line.withIndex()){
        val digit = char.digitToInt()
        val id = if (idx % 2 == 1) -1 else (cnt++)
            list += Segment(digit, id)
    }
    val a = part1(list)
    println(a)
}

data class Segment(val len: Int, val id: Int){
    val isEmpty
        get() = id == -1
}

private fun part1(list: List<Segment>) : Long{
    val materialized = mutableListOf<Int>()
    for (segment in list){
        repeat(segment.len){
            materialized.add(segment.id)
        }
    }
    while (true){
        val firstOpenSpace = materialized.indexOfFirst { i -> i == -1 }
        val lastUsedSpace = materialized.indexOfLast { i -> i != -1 }
        if (firstOpenSpace >= lastUsedSpace){
            break
        }
        materialized[firstOpenSpace] = materialized[lastUsedSpace]
        materialized[lastUsedSpace] = -1
    }
    var checkSum = 0L
    for ((idx , block) in materialized.withIndex()){
        if (block == -1) continue
        checkSum += idx * block
    }
    return checkSum
}