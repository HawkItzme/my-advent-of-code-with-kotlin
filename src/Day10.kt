fun main() {
    val input = readInput("Day10")
    val map = buildMapFromLines(input)
    val potentialTrailHeads = map.filterValues { it == 0 }.toList()
    val part1Result = potentialTrailHeads.sumOf { (traihead, _) ->
        countReachableSummitsForTrailHead(traihead, map)
    }.also(:: println)

    val part2Result = potentialTrailHeads.sumOf { (traihead, _) ->
        countDistinctPathsToSummitsForTrailHead(traihead, map)
    }.also(:: println)

    check(part1Result == 535)
    check(part2Result == 1186)

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

fun countDistinctPathsToSummitsForTrailHead(trailHeads: VecNew2, map: Map<VecNew2, Int>): Int{
    require(map[trailHeads] == 0)
    val paths: List<Path> = tracePathsToSummits(listOf(trailHeads), map)
    check(paths.all { path -> map.getCoordinate(path.steps.last()) == 9 })
    return paths.toSet().size
}

@JvmInline
value class Path(val steps: List<VecNew2>)

fun tracePathsToSummits(path: List<VecNew2>, map: Map<VecNew2, Int>): List<Path> {
    val curLoc = path.last()
    val curLevel = map.getCoordinate(curLoc)
    if (curLevel == 9) return  listOf(Path(path))
    val nextLevel = curLevel + 1
    val walkablePaths = with(curLoc){
        listOf(up(), down(), left(), right())
    }.filter{
        map.getCoordinate(it) == nextLevel
    }.flatMap{
        tracePathsToSummits(path + it, map)
    }
    return walkablePaths
}
