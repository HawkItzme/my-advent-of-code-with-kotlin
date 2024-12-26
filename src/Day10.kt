fun main() {
    val input = readInput("Day10")
    val map = buildMapFromLines(input)
    val potentialTrailHeads = map.filterValues { it == 0 }.toList()
    val part1Result = potentialTrailHeads.sumOf { (traihead, _) ->
        countReachableSummitsForTrailHead(traihead, map)
    }.also(:: println)

}

data class VecNew2(
    val x: Int,
    val y: Int
){
    fun up() = VecNew2(x, y - 1)
    fun down() = VecNew2(x, y + 1)
    fun left() = VecNew2(x - 1, y)
    fun right() = VecNew2(x + 1, y)
}

private fun buildMapFromLines(lines: List<String>) : Map<VecNew2, Int> = buildMap<VecNew2, Int> {
    for (y in lines.indices){
        for (x in lines[0].indices){
            put(VecNew2(x,y), lines[y][x].digitToInt())
        }
    }
}

fun Map<VecNew2, Int>.getCoordinate(v: VecNew2) = this[v] ?: Int.MAX_VALUE

fun countReachableSummitsForTrailHead(trailHead: VecNew2, map: Map<VecNew2, Int>) : Int{
    require(map[trailHead] == 0)
    val reachablesummits = walkPathsToSummits(listOf(trailHead), map)
    check(reachablesummits.all{summit -> map.getCoordinate(summit.location) == 9})
    return reachablesummits.toSet().size
}

@JvmInline
value class Summit(val location: VecNew2)

fun walkPathsToSummits(path: List<VecNew2>, map: Map<VecNew2, Int>): List<Summit>{
    val curLoc = path.last()
    val curLevel = map.getCoordinate(curLoc)
    if (curLevel == 9) return  listOf(Summit(curLoc))
    val nextLevel = curLevel + 1
    val reachableSummits = with(curLoc){
        listOf(up(), down(), left(), right())
    }.filter{
        map.getCoordinate(it) == nextLevel
    }.flatMap{
        walkPathsToSummits(path + it, map)
    }
    return reachableSummits
}