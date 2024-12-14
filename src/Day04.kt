fun main(){
    val input = readInput("Day04")
    val grid = Grid(input.map { it.toCharArray().toList() })
    part1(grid)
}

private fun part1(grid: Grid){
    var xmasWordCount = 0
    for ((startX : Int, startY: Int) in  grid.indices){
        xmasWordCount += grid.countXmasForPos(startX, startY)
    }
    println(xmasWordCount)
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
}