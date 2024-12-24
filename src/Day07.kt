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

    val partB = measureTimedValue { part2(calibs) }
    println(partB)
}

private fun part1(calibs: List<Calibration>): Long = calibs.filter { calibration ->
    calibration.isValid()
}.sumOf { calibration ->
    calibration.result
}

private fun part2(calibs : List<Calibration>) : Long = calibs.filter { calibration ->
    calibration.isValid2()
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


    fun isValid2(): Boolean{
        // Here, we generate Binary number that indicates
        // 1: times
        // 0: Plus
        // 2: Concat, then we iterate
        var operandSlots = operands.size - 1
        val possibleCombos = 3.0.pow(operandSlots.toDouble()).toInt()
        for (operatorCombinations in 0..<possibleCombos){
            var calcRes = operands[0]
            for (index in 0..<operands.lastIndex){
                val b = operands[index + 1]
                val opstr = operatorCombinations.toString(3)
                val op = opstr.getOrNull(opstr.lastIndex - index) ?: '0'
                when(op){
                    '0' -> calcRes = calcRes + b
                    '1' -> calcRes = calcRes * b
                    '2' -> calcRes = (calcRes.toString() + b.toString()).toLong()
                }
            }
            if (calcRes == result) return true
        }
        return false
    }











































}