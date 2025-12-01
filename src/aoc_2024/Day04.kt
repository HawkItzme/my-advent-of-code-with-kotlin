package aoc_2024

fun main(){
    val input = readInput("Day04")
    val grid = Grid(input.map { it.toCharArray().toList() })
    part1(grid)

    part2(grid)
}

private fun part1(grid: Grid){
    var xmasWordCount = 0
    for ((startX : Int, startY: Int) in  grid.indices){
        xmasWordCount += grid.countXmasForPos(startX, startY)
    }
    println(xmasWordCount)
}

private fun part2(grid: Grid){
    var crossCount = 0
    for ((centerX : Int, centerY: Int) in  grid.indices){
        crossCount += if (grid.countXMasCrossFromPos(centerX, centerY)) 1 else 0
    }
    println(crossCount)
}



data class Vector1(val x:Int, val y:Int)

data class Grid(private val ele : List<List<Char>>){

    val allowedDir: List<Vector1> = listOf(
        Vector1(1, 0),
        Vector1(1, 1),
        Vector1(0, 1),
        Vector1(-1, 1),
        Vector1(-1, 0),
        Vector1(-1, -1),
        Vector1(0, -1),
        Vector1(1, -1)
    )

    val indices: Sequence<Pair<Int, Int>> = sequence {
        for (y: Int in ele[0].indices){
            for (x: Int in ele.indices){
                yield(Pair(x,y))
            }
        }
    }

    fun getAtPos(x:Int , y:Int): Char? = ele.getOrNull(y)?.getOrNull(x)

    fun countXmasForPos(startX: Int, startY:Int):Int {
        return allowedDir.count { direction : Vector1 ->
            checXmasForDir(startX,startY, direction)
        }
    }

    fun checXmasForDir(startX: Int, startY: Int, dir: Vector1) :Boolean{
        var runningX :Int = startX
        var runningY : Int = startY

        for (char : Char in listOf('X', 'M', 'A', 'S')){
            if (getAtPos(runningX, runningY) != char){
                return false
            }
            runningY += dir.y
            runningX += dir.x
        }
        return true
    }

    fun countXMasCrossFromPos(centerX : Int, centerY: Int): Boolean{

        //For Invalid Center
        if (getAtPos(centerX, centerY) != 'A'){
            return false
        }

        val isBottomDiagonalMAS : Boolean =
            getAtPos(centerX - 1, centerY - 1) == 'M' && getAtPos(centerX + 1, centerY + 1) == 'S'
        val isBottomDiagonalSAM : Boolean =
            getAtPos(centerX - 1, centerY - 1) == 'S' && getAtPos(centerX + 1, centerY + 1) == 'M'
        val isBottomDiagonalFine : Boolean = isBottomDiagonalMAS || isBottomDiagonalSAM


        val isTopDiagonalMAS : Boolean =
            getAtPos(centerX - 1, centerY + 1) == 'M' && getAtPos(centerX + 1, centerY - 1) == 'S'
        val isTopDiagonalSAM : Boolean =
            getAtPos(centerX - 1, centerY + 1) == 'S' && getAtPos(centerX + 1, centerY - 1) == 'M'
        val isTopDiagonalFine : Boolean = isTopDiagonalSAM || isTopDiagonalMAS

        return isBottomDiagonalFine && isTopDiagonalFine
    }
}












































