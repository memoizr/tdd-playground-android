package memoizrlabs.com.tdd_playground.game_of_life

import memoizrlabs.com.tdd_playground.game_of_life.Status.Alive
import memoizrlabs.com.tdd_playground.game_of_life.Status.Dead

data class Board(val cells: Map<Point, Cell>) {

    fun neighbours(position: Point): List<Cell> {
        val above = position.oneAbove()
        val below = position.oneBelow()
        val left = position.oneLeft()
        val right = position.oneRight()
        val upperLeft = position.oneAbove().oneLeft()
        val upperRight = position.oneAbove().oneRight()
        val lowerLeft = position.oneBelow().oneLeft()
        val lowerRight = position.oneBelow().oneRight()

        return listOf(
                cells.get(above),
                cells.get(below),
                cells.get(left),
                cells.get(right),
                cells.get(upperLeft),
                cells.get(upperRight),
                cells.get(lowerLeft),
                cells.get(lowerRight)

        ).filterNotNull()
    }

    fun shouldSurvive(position: Point, status: Status): Cell {
        val neighbours = neighbours(position)
        val alive = neighbours.filter { it.isAlive() }.count()

        return when {
            status == Dead -> if (alive == 3) Cell(Alive) else Cell(Dead)
            alive < 2 -> Cell(Dead)
            alive > 3 -> Cell(Dead)
            else -> Cell(Alive)
        }
    }

    fun nextGeneration(): Board {
        return Board(cells.mapValues { shouldSurvive(it.key, it.value.status) })
    }
}

data class Point(val x: Int, val y: Int) {
    fun oneAbove() = copy(y = y - 1)
    fun oneBelow() = copy(y = y + 1)
    fun oneLeft() = copy(x = x - 1)
    fun oneRight() = copy(x = x + 1)
}

data class Cell(val status: Status) {
    fun isAlive() = status == Alive
    fun isDead() = !isAlive()
}

enum class Status {
    Alive, Dead
}
