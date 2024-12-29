
class Day12{
    fun main() {
        val input = readInput("Day12")
        println("PART 1: ${part1(input)}")
    }

    fun part1(farmLines: List<String>): Int{
        val allCoordinates = getAllCoordinates(farmLines)
        val regions = findFenceablePlantRegion(allCoordinates, farmLines)
        val areas = regions.map{ it.area}
        val perimeters = regions.map { it.perimeter }
        val prices = areas.zip(perimeters){

        }
        return prices.sum()

    }

    data class Vec2(val x:Int, val y: Int){

    }

    @JvmInline
    value class FenceableRegion(val coordinates: List<Vec2>){
        val area get() = coordinates.size
        val perimeter : Int
            get() = coordinates.size
        val sides : Int
            get() = coordinates.size
    }

    fun getAllCoordinates(lines: List<String>): List<Vec2> = buildList {
        for(y in lines.indices){
            for (x in lines[0].indices){
                add(Vec2(x,y))
            }
        }
    }

    fun findFenceablePlantRegion(
        allCoordinates : List<Vec2>,
        farmLines: List<String>
    ): List<FenceableRegion>{

    }
}

