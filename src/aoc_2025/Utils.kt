package aoc_2025

import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText

fun readInputAsListOfString(name : String) = Path("src/aoc_2025/input/$name.txt").readLines()


fun readInputAsString(name : String) = Path("src/aoc_2025/input/$name.txt").readText().trim()