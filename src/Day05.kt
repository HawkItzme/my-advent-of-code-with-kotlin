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

    fun productPartiallyBetterUpdate(rules: List<OrderingRule>) : Update{
        for (rule in rules){
            if (isSingleRuleValid(rule)){
                continue
            }
            val newOrder = this.numbers.toMutableList()
            val beforeIndex = newOrder.indexOf(rule.before)
            val afterIndex = newOrder.indexOf(rule.after)
            newOrder[beforeIndex] = rule.after
            newOrder[afterIndex] = rule.before
            val new = Update(newOrder)
            check(new.isSingleRuleValid(rule))

            return new
        }
        return this
    }

    fun productTotallyBetterUpdate(rules: List<OrderingRule>): Update{
        var curr = this
        do{
            curr = curr.productPartiallyBetterUpdate(rules)
        }while (!curr.isValid(rules))
        return curr
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
    val part2Sol = measureTimedValue { part2(updates, orderingRules) }

    println(part1Sol)
    println(part2Sol)
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

private fun part2(
    updates: List<Update>,
    orderingRules: List<OrderingRule>
): Int{
    val invalidUpdates = updates.filterNot { update: Update ->
        update.isValid(orderingRules)
    }
    return invalidUpdates.sumOf {
        it.productTotallyBetterUpdate(orderingRules)
            .middleNum()
    }
}