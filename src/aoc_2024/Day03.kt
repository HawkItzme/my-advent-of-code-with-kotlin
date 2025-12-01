package aoc_2024

fun main() {
    val input = readInput("Day03")

    val part1Output = part1(input)

    val part2Output = part2(input)

    println(part1Output)
    println(part2Output)
}

private fun part1(report : List<String>) : Long {
    return report.sumOf { addValidMul(it) }
}

private fun part2(report : List<String>) : Long {
    return addValidMul2(report.joinToString())
}

private const val mulRegex = """mul\((\d{1,3}),(\d{1,3})\)"""

private const val doRegex = """do\(\)"""

private const val dontRegex = """don't\(\)"""

private fun addValidMul(input : String) : Long{
 return mulRegex.toRegex().findAll(input).sumOf {
     val (first : String, sec : String) = it.destructured
     first.toLong() * sec.toLong()
 }
}

private fun addValidMul2(input : String) : Long{
    var sum = 0L
    var enabled = true

    """$mulRegex|$doRegex|$dontRegex""".toRegex().findAll(input).forEach { match ->
        when(match.value){
            "don't()" -> enabled = false
            "do()" -> enabled = true
            else -> if (enabled) sum += match.mulNums()
        }
    }
    return sum
}

private fun MatchResult.mulNums(): Long{
    val (first : String, sec : String) = destructured
    return first.toLong() * sec.toLong()
}