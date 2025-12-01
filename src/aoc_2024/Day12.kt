package aoc_2024

import kotlin.math.abs

fun main() {
        val input = readInput("Day12")
        println("PART 1: ${part_1(input)}")
        println("PART 2: ${part_2(input)}")
    }

    fun part_1(farmLines: List<String>): Int{
        val allCoordinates = getAllCoordinates(farmLines)
        val regions = findFenceablePlantRegion(allCoordinates, farmLines)
        val areas = regions.map{ it.area}
        val perimeters = regions.map { it.perimeter }
        val prices = areas.zip(perimeters){area , perimeter ->
            area * perimeter
        }
        return prices.sum()

    }

    fun part_2(farmLines: List<String>): Int{
        val allCoordinates = getAllCoordinates(farmLines)
        val regions = findFenceablePlantRegion(allCoordinates, farmLines)
        val areas = regions.map{ it.area}
        val sides = regions.map { it.sides }
        val prices = areas.zip(sides){area , sides ->
            area * sides
        }
        return prices.sum()

    }

    data class VecNew3(val x:Int, val y: Int){
        fun up() = VecNew3(x, y - 1)
        fun down() = VecNew3(x, y + 1)
        fun left() = VecNew3(x - 1, y)
        fun right() = VecNew3(x + 1, y)
        operator fun plus(other : VecNew3) = VecNew3(x + other.x, y + other.y)
        fun dist(other : VecNew3): Int = abs(x - other.x) + abs(y - other.y)
    }

    @JvmInline
    value class FenceableRegion(val coordinates: List<VecNew3>){
        val area get() = coordinates.size
        val perimeter : Int
            get() {
                val plotAdjacentLocations = buildList<VecNew3> {
                    for (loc in coordinates){
                        val neighbors : List<VecNew3> = with(loc){
                            listOf(up(), down(), left(), right())
                        }
                        val adjacentNeighbor =
                            neighbors.filter{
                                VecNew3 -> VecNew3 !in coordinates
                            }
                        addAll(adjacentNeighbor)
                    }
                }
                return plotAdjacentLocations.size
            }
        val sides : Int
            get() {
                val xRange = (coordinates.minOf { vecNew3 -> vecNew3.x }) - 1..(coordinates.maxOf { it.x }) + 1
                val yRange = (coordinates.minOf { vecNew3 -> vecNew3.y }) - 1..(coordinates.maxOf { it.y }) + 1

                val horizontalTopEdges = buildList{
                    for(y in yRange){
                        add(buildList {
                            for (x in xRange){
                                val loc = VecNew3(x,y)
                                if (loc !in coordinates && loc.down() in coordinates){
                                    add(loc)
                                }
                            }
                        })
                    }
                }
                val horizontalBottomEdges = buildList{
                    for(y in yRange){
                        add(buildList {
                            for (x in xRange){
                                val loc = VecNew3(x,y)
                                if (loc !in coordinates && loc.up() in coordinates){
                                    add(loc)
                                }
                            }
                        })
                    }
                }
                val verticalLeftEdges = buildList{
                    for(x in xRange){
                        add(buildList {
                            for (y in yRange){
                                val loc = VecNew3(x,y)
                                if (loc !in coordinates && loc.right() in coordinates){
                                    add(loc)
                                }
                            }
                        })
                    }
                }
                val verticalRightEdges = buildList{
                    for(x in xRange){
                        add(buildList {
                            for (y in yRange){
                                val loc = VecNew3(x,y)
                                if (loc !in coordinates && loc.left() in coordinates){
                                    add(loc)
                                }
                            }
                        })
                    }
                }
                val horizontalTopSegment = horizontalTopEdges.sumOf { countContiguousGroups(it) }
                val horizontalBottomSegment = horizontalBottomEdges.sumOf { countContiguousGroups(it) }
                val verticalLeftSegment = verticalLeftEdges.sumOf { countContiguousGroups(it) }
                val verticalRightSegment = verticalRightEdges.sumOf { countContiguousGroups(it) }
                return horizontalTopSegment + horizontalBottomSegment + verticalLeftSegment + verticalRightSegment
            }
    }

    fun countContiguousGroups(list : List<VecNew3>): Int{
        if (list.isEmpty()) return  0
        val allGroups = mutableListOf<List<VecNew3>>()
        var currGroups = mutableListOf<VecNew3>()
        for (elem in list){
            if (currGroups.isEmpty() || currGroups.last().dist(elem) <= 1){
                currGroups += elem
            }else{
                allGroups.add(currGroups)
                currGroups = mutableListOf<VecNew3>()
                currGroups.add(elem)
            }
        }
        if (currGroups.isNotEmpty()){
            allGroups.add(currGroups)
        }
        return allGroups.size
    }

    fun getAllCoordinates(lines: List<String>): List<VecNew3> = buildList {
        for(y in lines.indices){
            for (x in lines[0].indices){
                add(VecNew3(x,y))
            }
        }
    }

    fun findFenceablePlantRegion(
        allCoordinates : List<VecNew3>,
        farmLines: List<String>
    ): List<FenceableRegion>{
        val untouchableCoordinates = allCoordinates.toMutableList()
        val regions = buildList {
            while (untouchableCoordinates.isNotEmpty()){
                val region = farmLines.findRegionForCoordinate(untouchableCoordinates.first())
                untouchableCoordinates.removeAll(region.coordinates)
                add(region)
            }
        }
        return regions
    }

fun List<String>.findRegionForCoordinate(VecNew3: VecNew3): FenceableRegion{
    fun List<String>.getPlantAt(v : VecNew3) : Char = if (v.x in this[0].indices && v.y in this.indices) this[v.y][v.x] else '?'
    val plantType : Char = getPlantAt(VecNew3)
    val coordinates = buildList<VecNew3> {
        val locationsToCheck = ArrayList<VecNew3>(listOf(VecNew3))
        add(VecNew3)
        while(locationsToCheck.isNotEmpty()){
            val currLocation = locationsToCheck.removeFirst()
            val neighbors = with(currLocation){
                listOf(up(), down(), left(), right())
            }
            val validNeighbors = neighbors.filter { vecNew2 -> getPlantAt(vecNew2) == plantType }
            val newNeighbor = validNeighbors.filter { VecNew3 -> VecNew3 !in this }
            locationsToCheck.addAll(newNeighbor)
            this.addAll(newNeighbor)
        }
    }
    return FenceableRegion(coordinates)
}