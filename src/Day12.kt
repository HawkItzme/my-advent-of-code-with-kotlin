
    fun main() {
        val input = readInput("Day12")
        println("PART 1: ${part_1(input)}")
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
            get() = coordinates.size
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