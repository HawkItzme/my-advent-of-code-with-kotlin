package aoc_2024

import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.readText
import kotlin.io.path.Path

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/aoc_2024/input/$name.txt").readText().trim().lines()

/**
 * Converts string to aoc_2024.md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)
