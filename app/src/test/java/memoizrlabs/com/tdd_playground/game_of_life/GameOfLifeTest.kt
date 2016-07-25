package memoizrlabs.com.tdd_playground.game_of_life

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class GameOfLifeTest {
    @Test
    fun withNoNeighboursCountIsZero() {
        val board = aBoard(at(0, 0))
        assertThat(board.neighbours(at(0, 0)).count(), equalTo(0))
    }

    @Test
    fun withNeighboursReturnsVerticalColumn() {
        val board = aBoard(at(0, 0), at(0, 1), at(0, 2))
        println(board)
        println(board.neighbours(at(0, 1)))
        assertThat(board.neighbours(at(0, 1)).count(), equalTo(2))
    }

    @Test
    fun withNeighboursReturnsHorizontalRow() {
        val board = aBoard(at(0, 0), at(1, 0), at(2, 0))
        assertThat(board.neighbours(at(1, 0)).count(), equalTo(2))
    }

    @Test
    fun diagonals() {
        val upperLeft = at(0, 0)
        val upperRight = at(0, 2)
        val lowerLeft = at(2, 0)
        val lowerRight = at(2, 2)
        val center = at (1, 1)
        val board = aBoard(upperLeft, upperRight, lowerLeft, lowerRight)

        assertThat(board.neighbours(center).count(), equalTo(4))
    }

    @Test
    fun aCellWithFewerThanTwoAliveNeighboursDies() {
        val before = Board(mapOf(
                at(0, 0) to aDeadCell(),
                at(1, 0) to aLiveCell(),
                at(2, 0) to aDeadCell()
        ))
        val after = Board(mapOf(
                at(0, 0) to aDeadCell(),
                at(1, 0) to aDeadCell(),
                at(2, 0) to aDeadCell()
        ))
        assertThat(before.nextGeneration(), equalTo(after))
    }

    @Test
    fun aCellWithMoreThanThreeAliveNeighboursDies() {
        val before = Board(mapOf(
                at(1, 0) to aLiveCell(),
                at(1, 1) to aLiveCell(),
                at(1, 2) to aLiveCell(),
                at(0, 1) to aLiveCell(),
                at(2, 1) to aLiveCell()
        ))
        val after = Board(mapOf(
                at(1, 0) to aLiveCell(),
                at(1, 1) to aDeadCell(),
                at(1, 2) to aLiveCell(),
                at(0, 1) to aLiveCell(),
                at(2, 1) to aLiveCell()
        ))
        assertThat(before.nextGeneration(), equalTo(after))
    }

    @Test
    fun aDeadCellWithExactlyThreeAliveNeighboursBecomesAlive() {
        val before = Board(mapOf(
                at(1, 0) to aLiveCell(),
                at(1, 1) to aDeadCell(),
                at(1, 2) to aLiveCell(),
                at(0, 1) to aLiveCell()
        ))
        val after = Board(mapOf(
                at(1, 0) to aDeadCell(),
                at(1, 1) to aLiveCell(),
                at(1, 2) to aDeadCell(),
                at(0, 1) to aLiveCell()
        ))
        assertThat(before.nextGeneration(), equalTo(after))
    }
}

fun aLiveCell() = Cell(Status.Alive)
fun aDeadCell() = Cell(Status.Dead)
fun aBoard(vararg points: Point) = Board(points.map { Pair(it, Cell(Status.Dead)) }.toMap())
fun at(x: Int, y: Int): Point = Point(x, y)
