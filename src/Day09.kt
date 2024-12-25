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
    val b = part2(list)
    println(b)
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

class BlockStorage(list: List<Segment>){
    val storage = list.toMutableList()

    fun firstGapOfMinimumSize(n: Int, beforeIndex: Int): Int?{
        val index = storage.indexOfFirst { segment -> segment.id == -1 && segment.len >= n }
        if (index >= beforeIndex) return null
        if (index == -1) return null
        return index
    }

    fun moveSegmentIntoGap(segmentIdx: Int, gapIdx : Int){
        val gap = storage[gapIdx]
        val segment  = storage[segmentIdx]
        val newGapLength = gap.len - segment.len
        val newGapSegment = Segment(newGapLength, -1)
        storage[segmentIdx] = Segment(segment.len, -1)
        storage[gapIdx] = segment
        if (newGapSegment.len > 0){
            storage.add(gapIdx + 1, newGapSegment)
        }
    }

    fun materialize() : List<Int>{
        return storage.flatMap { segment ->
            List(segment.len) {
                segment.id
            }
        }
    }

    fun checkSum() : Long{
        return materialize()
            .withIndex()
            .filter { (_, block) -> block != -1}
            .sumOf { (idx, block) -> idx.toLong() * block }
    }

}

private fun attemptMove(blockStorage : BlockStorage): Boolean{
    for ((idx, segment) in blockStorage.storage.withIndex().reversed()){
        if (segment.isEmpty) continue
        val target = blockStorage.firstGapOfMinimumSize(n = segment.len, beforeIndex = idx) ?: continue
        blockStorage.moveSegmentIntoGap(idx, target)
        return true
    }
    return false
}

private fun part2(list : List<Segment>) : Long{
    val blockStorage = BlockStorage(list)
    do {
        val didMoveSuccessfully = attemptMove(blockStorage)
    }while (didMoveSuccessfully)
    return blockStorage.checkSum()
}