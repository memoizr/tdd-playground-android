package memoizrlabs.com.tdd_playground.game_of_life

import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class GameTest {
    @Test
    fun calculatesNextGeneration() {
        assertThat(Generation(firstGeneration).nextGeneration().toString(), equalTo(Generation(secondGeneration).toString()))
    }
}

class NeighgbourFinderTest {
    @Test
    fun findsNeighbours() {
        assertThat(NeighgbourFinder(firstGeneration).findNeighboursFor(0, 0), equalTo(listOf(".", ".", ".")))
        assertThat(NeighgbourFinder(firstGeneration).findNeighboursFor(3, 2), equalTo(listOf(".", ".", "*", "*", ".", ".", ".", ".")))
        assertThat(NeighgbourFinder(firstGeneration).findNeighboursFor(7, 3), equalTo(listOf(".", ".", ".")))
    }

    @Test
    fun foo() {
        var generation = Generation(firstGeneration)
        for (i in 1..100) {
            println(generation)
            generation = generation.nextGeneration()
        }
    }
}

class NeighgbourFinder(state: String) {
    private val list = state.split("\n").filter { it.isNotEmpty() }
    private val height = list.size
    private val width = list.first().length

    fun findNeighboursFor(x: Int, y: Int): List<String> {
        val currentRow = list[y]
        val previousRow: String? = if (y > 0) list[y - 1] else null
        val nextRow: String? = if (y < height-1) list[y + 1] else null
        return listOf(previousRow?.previousItemOrNull(x), previousRow?.get(x), previousRow?.nextItemOrNull(x),
                currentRow.nextItemOrNull(x),
                nextRow?.nextItemOrNull(x), nextRow?.get(x), nextRow?.previousItemOrNull(x),
                currentRow.previousItemOrNull(x)).filterNotNull().map { it.toString() }
    }

    fun String.previousItemOrNull(index: Int): String? {
        return when {
            index <= 0 -> null
            else -> this[index-1].toString()
        }
    }

    fun String.nextItemOrNull(index: Int): String? {
        return when {
            index == width -1 -> null
            else -> this[index+1].toString()
        }
    }

}

class Generation(private val state: String) {
    val nf = NeighgbourFinder(state)

    fun nextGeneration(): Generation = Generation(state
            .split("\n")
            .filter { it.isNotEmpty() }
            .mapIndexed { y, row -> row.mapIndexed { x, cell -> if (shouldLive(x, y, cell.toString())) '*' else '.' }.joinToString("") }
            .joinToString("\n"))

    private fun shouldLive(x: Int, y: Int, cell: String): Boolean{
        val neighbours = nf.findNeighboursFor(x, y)
        val alive = neighbours.filter { it == "*" }.count()
        return if (cell == "*") {
            when {
                alive < 2 -> false
                alive > 3 -> false
                else -> true
            }
        } else {
            alive == 3
        }
    }

    override fun toString(): String {
        return state.toString()
    }
}

//val firstGeneration = """........
//....*...
//...**...
//........
//"""
val firstGeneration = """........*
....*..*.
*.***.**.
...*...*.
..***.**.
*..*...*.
..*...**.
"""

val secondGeneration = """........
...**...
...**...
........"""