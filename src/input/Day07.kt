package input

import readInput
import kotlin.math.pow
import kotlin.time.measureTimedValue

fun main(){
    val input = readInput("Day07")
    val calibs = input.map { line ->
        val (res : String, ops: String) = line.split(": ")
        Calibration(res.toLong(), ops.split(" ").map { num -> num.toLong() })
    }
    val partA = measureTimedValue { part1(calibs) }
    println(partA)
   // part1(lines)
  //  part2(lines)
}

private fun part1(calibs: List<Calibration>): Long = calibs.filter { calibration ->
    calibration.isValid()
}.sumOf { calibration ->
    calibration.result
}

data class Calibration(val result : Long, val operands: List<Long>){

    fun isValid(): Boolean{
        // Here, we generate Binary number that indicates
        // 1: times
        // 0: Plus , then we iterate
        var operandSlots = operands.size - 1
        val possibleCombos = 2.0.pow(operandSlots.toDouble()).toInt()
        for (operatorCombinations in 0..<possibleCombos){
            var calcRes = operands[0]
            for (index in 0..<operands.lastIndex){
                val b = operands[index + 1]
                val op = (operatorCombinations shr index) and 0x1
                when(op){
                    0 -> calcRes = calcRes + b
                    1 -> calcRes = calcRes * b
                }
            }
            if (calcRes == result) return true
        }
        return false
    }
}