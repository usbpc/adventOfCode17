package advent2018

import xyz.usbpc.aoc.Day
import xyz.usbpc.aoc.inputgetter.AdventOfCode
import java.util.*

class Day09(override val adventOfCode: AdventOfCode) : Day {
    override val day: Int = 9
    private val input = adventOfCode.getInput(2018, day).extractInts()

    private fun Int.isWorthPoints() = (this % 23) == 0

    class CirclePlayfield<E>(init: E) {
        var head: ListItem<E>
        init {
            head = ListItem(item = init)
        }

        fun insertMarble(num: E) {
            val before = head.next
            val after = before.next

            val new = ListItem(num)
            new.prev = before
            new.next = after

            before.next = new
            after.prev = new

            head = new
        }

        fun removeMarble() : E {
            val toRemove = head.prev.prev.prev.prev.prev.prev.prev

            toRemove.prev.next = toRemove.next
            toRemove.next.prev = toRemove.prev

            head = toRemove.next

            return toRemove.item
        }

        class ListItem<E>(val item: E) {
            var next: ListItem<E> = this
            var prev: ListItem<E> = this

        }
    }

    private fun calculateWinningScore(maxElf: Int, maxMarble: Int) : Long {
        val playarea = CirclePlayfield(0)

        val scores = LongArray(maxElf)

        var nextMarbleNum = 1

        var currentElf = 0

        while (nextMarbleNum <= maxMarble) {
            if (nextMarbleNum.isWorthPoints()) {
                scores[currentElf] += playarea.removeMarble().toLong() + nextMarbleNum++
            } else {
                playarea.insertMarble(nextMarbleNum++)
            }
            //println(playarea.printLikeAoc(currentElf+1, curMarble))
            currentElf = (currentElf+1) % maxElf
        }
        return scores.max()!!
    }

    override fun part1(): String {
        return "" + calculateWinningScore(input[0], input[1])
    }

    override fun part2(): String {
        return "" + calculateWinningScore(input[0], input[1]*100)
    }
}