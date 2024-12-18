import kotlin.time.measureTimedValue

data class OrderingRule(val before: Int, val after: Int)

data class Update(val numbers: List<Int>){
    fun isValid(rules: List<OrderingRule>): Boolean{
        return rules.all { rule: OrderingRule ->
            isSingleRuleValid(rule)
        }
    }

    fun isSingleRuleValid(rule: OrderingRule): Boolean{
        val beforeLoc: Int = numbers.indexOf(rule.before)
        val afterLoc: Int = numbers.indexOf(rule.after)
        if (beforeLoc == -1 || afterLoc == -1){
            return true
        }
        return beforeLoc < afterLoc
    }

    fun middleNum(): Int{
        return numbers[numbers.lastIndex / 2]
    }
}


fun main(){
    val input = readInput("Day05")
    val orderingRules = input
        .takeWhile { line -> line.isNotBlank() }
        .map {
            val (before: String, after: String) = it.split("|")
            OrderingRule(before.toInt(), after.toInt())
        }
    val updates = input
        .takeLastWhile { line -> line.isNotBlank() }
        .map { line ->
            Update(line.split(",").map { it.toInt() })
        }
    val part1Sol = measureTimedValue { part1(updates, orderingRules) }

    println(part1Sol)
}

private fun part1(
    updates: List<Update>,
    orderingRules: List<OrderingRule>
): Int{
    return updates.sumOf {
        if (it.isValid(orderingRules)){
            it.middleNum()
        }else{
            0
        }
    }
}