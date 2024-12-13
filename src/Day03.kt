fun main() {
    val input = readInput("Day03")

    val part1Output = part1(input)

    println(part1Output)
}

private fun part1(report : List<String>) : Long {
    return report.sumOf { addValidMul(it) }
}

private const val mulRegex = """mul\((\d{1,3}),(\d{1,3})\)"""

private fun addValidMul(input : String) : Long{
 return mulRegex.toRegex().findAll(input).sumOf {
     val (first : String, sec : String) = it.destructured
     first.toLong() * sec.toLong()
 }
}