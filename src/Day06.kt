import java.awt.geom.IllegalPathStateException

fun main(){
    val input = readInput("Day06")
    val lines : List<List<Char>> = input.map { string: String ->
        string.toList()
    }
    part1(lines)
}

private fun part1(lines: List<List<Char>> ){
    val visited = simulateGuardPath(lines)

    println(visited.onSuccess{
        value -> value.distinctBy {
            record -> record.loc
    }.size
        val distinctCount = value.distinctBy { record -> record.loc }.size
        println("Total distinct success values: $distinctCount")
    })
}

data class GuardWalkRecord(val loc : Vec2, val heading: GuardHeading){

    fun step(): GuardWalkRecord{
        return GuardWalkRecord(loc + heading.step, heading)
    }

    fun turnRight(): GuardWalkRecord{
        return GuardWalkRecord(loc, heading.turnRight())
    }
}

data class Vec2(val x: Int, val y: Int){
    operator fun plus(other: Vec2) = Vec2(this.x + other.x, this.y + other.y)
}

enum class GuardHeading(val step: Vec2){
    NORTH(Vec2(0, -1)),
    SOUTH(Vec2(0, 1)),
    EAST(Vec2(1, 0)),
    WEST(Vec2(-1, 0));

    fun turnRight(): GuardHeading{
        return when(this){
            NORTH -> EAST
            SOUTH -> WEST
            EAST -> SOUTH
            WEST -> NORTH
        }
    }
}

fun getGuardStartingPosition(lines: List<List<Char>>) : Vec2{
    for (l in lines.indices){
        for (c in lines[0].indices){
            if (lines[l][c] == '^'){
                return Vec2(c, l)
            }
        }
    }
    error("No guard starting position found.")
}

private fun simulateGuardPath(lines : List<List<Char>>) : Result<List<GuardWalkRecord>> {
    var currGuardRecord = GuardWalkRecord(getGuardStartingPosition(lines), GuardHeading.NORTH)
    val visited = mutableListOf<GuardWalkRecord>(currGuardRecord)
    fun getTile(x: Int, y:Int): Char{
        return if(y !in lines.indices || x !in lines[0].indices) '?' else lines[y][x]
    }

    while (currGuardRecord.loc.y in lines.indices && currGuardRecord.loc.x in lines[0].indices){
        val attemptedNewPos = currGuardRecord.step()
        val targeTile = getTile(attemptedNewPos.loc.x, attemptedNewPos.loc.y)
        when(targeTile){
            '.', '^' -> {
                currGuardRecord = attemptedNewPos
                if (currGuardRecord in visited){
                    println("this file is familiar.")
                    return Result.failure(IllegalPathStateException())
                }
                visited += currGuardRecord
            }
            '#' -> {
                currGuardRecord = currGuardRecord.turnRight()
            }
            '?' -> {
                break
            }
        }
    }
    return Result.success(visited)
}