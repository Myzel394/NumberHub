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

        val rightBracketPosition =
            findRightBracket(position);
        val leftBracketPosition =
            findLeftBracket(position)
                ?: return TextRange(
                    rightBracketPosition?.let { takeNextIfIsOperator(it) }
                        ?.let { if (it > position) 0 else it } ?: 0,
                    position,
                );

        if (rightBracketPosition == null) {
            val rightBracketRelativeToLeftPosition = findRightBracket(leftBracketPosition + 1)

            return if (rightBracketRelativeToLeftPosition == null) {
                // 1+2(+5|+6
                TextRange((leftBracketPosition + 1).coerceAtMost(position), position)
            } else {
                // 1+2(6)+5|+6
                TextRange(
                    takeNextIfIsOperator(rightBracketRelativeToLeftPosition + 1).coerceAtMost(
                        position,
                    ),
                    position,
                )
            }
        }

        val end = findClosingParen(leftBracketPosition)
        return TextRange((leftBracketPosition + 1).coerceAtMost(end), end);
    }

    private fun takeNextIfIsOperator(position: Int): Int {
        if (position + 1 < value.length && Token.Operator.allWithoutBrackets.contains(value[position].toString())) {
            return position + 1
        }

        return position
    }

    private fun isSelectionARange(): Boolean = selection.start != selection.end

    private fun findLeftBracket(startPosition: Int): Int? {
        for (index in startPosition.coerceAtMost(value.length - 1) downTo 0) {
            if (value[index] == Token.Operator.leftBracket[0]) {
                return index
            }
        }

        return null
    }

    private fun findRightBracket(startPosition: Int): Int? {
        for (index in startPosition.coerceAtMost(value.length - 1) until value.length) {
            if (value[index] == Token.Operator.rightBracket[0]) {
                return index
            }
        }

        return null
    }

    // Based of https://stackoverflow.com/a/12752226/9878135
    fun findClosingParen(openPos: Int): Int {
        var closePos = openPos
        var counter = 1

        while (counter > 0) {
            val c = value[++closePos]
            if (c == Token.Operator.leftBracket[0]) {
                counter++
            } else if (c == Token.Operator.rightBracket[0]) {
                counter--
            }
        }
        return closePos
    }
}