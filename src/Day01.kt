import kotlin.math.abs

fun main(){
    val lines = readInput("Day01")
    val (left, right) = lines.map { line ->
        val first = line.substringBefore(" ").toInt()
        val second = line.substringAfterLast(" ").toInt()
        first to second
    }.unzip()

    val result =  left.sorted().zip(right.sorted()).map { (first, second) ->
        abs(first - second)
    }.sum()

    println(result)
}