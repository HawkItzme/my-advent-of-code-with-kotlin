package aoc_2024

import kotlin.time.measureTimedValue

fun main(){
    val input = readInput("Day08")
    val mapYRange = input.indices
    val mapXRange = input[0].indices
    val typeToPos = createLookup(input)
    val antiNodeLocation1 = measureTimedValue {
        part1(
            typeToPosition = typeToPos
        ){
            (x,y) -> x in mapXRange && y in mapYRange
        }
    }
    val antiNodeLocation2 = measureTimedValue {
        part2(
            typeToPosition = typeToPos
        ){
                (x,y) -> x in mapXRange && y in mapYRange
        }
    }

    println("${antiNodeLocation1.value.size} in ${antiNodeLocation1.duration}")
    println("${antiNodeLocation2.value.size} in ${antiNodeLocation2.duration}")

}

 data class VecNew(val x: Int, val y: Int){
    operator fun minus(other: VecNew) = VecNew(this.x - other.x, this.y - other.y)
    operator fun plus(other: VecNew) = VecNew(this.x + other.x, this.y + other.y)
    operator fun times(scaler: Int) = VecNew(this.x * scaler, this.y * scaler)
}

fun List<String>.getCharacter(x: Int, y:Int) = this[y][x]

private fun createLookup(lines: List<String>): Map<Char, Set<VecNew>> = buildMap <Char, MutableSet<VecNew>>{
    for (y in lines.indices){
        for (x in lines[0].indices){
            val chr = lines.getCharacter(x, y)
            if (chr == '.'){
                continue
            }
            val ttpEntry = this.getOrElse(chr){ mutableSetOf() }
            ttpEntry.add(VecNew(x, y))
            this.put(chr, ttpEntry)
        }
    }
}

fun part1(
    typeToPosition : Map<Char, Set<VecNew>>,
    isInBounds: (VecNew) -> Boolean
): Set<VecNew> = buildSet {
    for (antennaType  in typeToPosition.keys){
        for (first: VecNew in typeToPosition[antennaType]!!){
            for (second: VecNew in typeToPosition[antennaType]!!){
                if (first == second) continue
                val distanceVec : VecNew = second - first
                val relDistVec: VecNew = distanceVec * 2
                val absAntiNodeVec = first + relDistVec
                if (isInBounds(absAntiNodeVec)){
                    add(absAntiNodeVec)
                }
            }
        }
    }
}

fun part2(
    typeToPosition : Map<Char, Set<VecNew>>,
    isInBounds: (VecNew) -> Boolean
): Set<VecNew> = buildSet {
    for (antennaType  in typeToPosition.keys){
        for (first: VecNew in typeToPosition[antennaType]!!){
            for (second: VecNew in typeToPosition[antennaType]!!){
                if (first == second) continue
                val distanceVec : VecNew = second - first
                val relDistVec: VecNew = distanceVec
                var positiveNextLocation  = first
                do {
                    positiveNextLocation = positiveNextLocation + relDistVec
                    if (isInBounds(positiveNextLocation)){
                        this.add(positiveNextLocation)
                    }
                }while (isInBounds(positiveNextLocation))
                var negativeNextLocation = first
                do {
                    negativeNextLocation = negativeNextLocation - relDistVec
                    if (isInBounds(negativeNextLocation)){
                        this.add(negativeNextLocation)
                    }
                }while (isInBounds(negativeNextLocation))
            }
        }
    }
}











































