package app.myzel394.numberhub.core.ui.common.textfield

import androidx.compose.ui.text.TextRange
import app.myzel394.numberhub.core.base.Token

/** Smartly delete tokens
 * @param value the current value of the text field
 * @param selection the current selection of the text field - Assumed to be a valid selection
 */
data class SmartDeleteHandler(
    private val value: String,
    private val selection: TextRange,
) {
    /**
     * Calculate the range to delete based on the current selection.
     *
     * @return the range to delete - [Inclusive, Exclusive]
     */
    fun calculateDeleteRange(): TextRange {
        if (value == "") {
            return TextRange(0, 0)
        }

        if (isSelectionARange()) {
            return selection
        }

        val position = selection.start

        when (position) {
            0 -> return TextRange(0, 0)
            1 -> return TextRange(0, 1)
        }

        val bracketPos = findPreviousBracket(position.coerceAtMost(value.length - 1) - 1)

        if (bracketPos == null) {
            return TextRange(0, position)
        }

        val isAtLeftEdge =
            position - 1 == bracketPos && value[bracketPos] == Token.Operator.leftBracket[0]
        val isAtRightEdge =
            position - 1 == bracketPos && value[bracketPos] == Token.Operator.rightBracket[0]

        if (!isAtLeftEdge && !isAtRightEdge) {
            return TextRange(bracketPos + 1, position)
        }

        if (isAtRightEdge) {
            val leftBracketPos = findClosingParenBackwards(bracketPos)

            return if (leftBracketPos != null) {
                TextRange(leftBracketPos + 1, position)
            } else {
                // Weird case, should not happen
                TextRange(0, position + 1)
            }
        }

        val rightBracketPos = findClosingParen(bracketPos)

        if (rightBracketPos != null) {
            return TextRange(bracketPos + 1, rightBracketPos)
        }

        // Find previous bracket and return range from there to cursor position
        val previousBracketPos = findPreviousBracket(bracketPos - 1)?.let { it + 1 } ?: 0

        return TextRange(previousBracketPos, position)
    }

    private fun takeNextIfIsOperator(position: Int): Int {
        if (position + 1 < value.length && Token.Operator.allWithoutBrackets.contains(value[position].toString())) {
            return position + 1
        }

        return position
    }

    private fun isSelectionARange(): Boolean = selection.start != selection.end

    private fun findPreviousBracket(startPosition: Int): Int? {
        for (index in startPosition.coerceAtMost(value.length - 1) downTo 0) {
            if (value[index] == Token.Operator.rightBracket[0] || value[index] == Token.Operator.leftBracket[0]) {
                return index
            }
        }

        return null
    }

    private fun findLeftBracket(startPosition: Int): Int? {
        for (index in startPosition.coerceAtMost(value.length - 1) downTo 0) {
            if (value[index] == Token.Operator.leftBracket[0]) {
                return index
            }
        }

        return null
    }

    private fun findRightBracket(startPosition: Int): Int? {
        for (index in startPosition.coerceAtMost(value.length - 1) downTo 0) {
            if (value[index] == Token.Operator.rightBracket[0]) {
                return index
            }
        }

        return null
    }

    private fun isAtEdge(position: Int): Boolean {
        if (position == 0) {
            return false
        }

        val previousCharacter = value[position.coerceAtMost(value.length - 1) - 1]

        return previousCharacter == Token.Operator.leftBracket[0] || previousCharacter == Token.Operator.rightBracket[0]
    }

    // Based of https://stackoverflow.com/a/12752226/9878135
    fun findClosingParen(openPos: Int): Int? {
        var closePos = openPos
        var counter = 1

        while (counter > 0) {
            val nextPos = ++closePos

            if (nextPos >= value.length) {
                return null
            }

            val c = value[nextPos]
            if (c == Token.Operator.leftBracket[0]) {
                counter++
            } else if (c == Token.Operator.rightBracket[0]) {
                counter--
            }
        }

        if (closePos == openPos) {
            return null
        }

        return closePos
    }

    fun findClosingParenBackwards(openPos: Int): Int? {
        var closePos = openPos
        var counter = 1

        while (counter > 0) {
            val c = value[--closePos]
            if (c == Token.Operator.leftBracket[0]) {
                counter--
            } else if (c == Token.Operator.rightBracket[0]) {
                counter++
            }
        }

        if (closePos == openPos) {
            return null
        }

        return closePos
    }
}