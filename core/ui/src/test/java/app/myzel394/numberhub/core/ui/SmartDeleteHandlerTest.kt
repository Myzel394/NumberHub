package app.myzel394.numberhub.core.ui

import androidx.compose.ui.text.TextRange
import app.myzel394.numberhub.core.ui.common.textfield.SmartDeleteHandler
import org.junit.Test

class SmartDeleteHandlerTest {
    @Test
    fun `test simple delete`() {
        assertDeleteRange(
            "1+2",
            TextRange(2, 2),
            TextRange(0, 2),
        )
    }

    @Test
    fun `test brackets, position in between`() {
        assertDeleteRange(
            "1+(2+3)",
            TextRange(4, 4),
            TextRange(3, 4),
        )
    }

    @Test
    fun `test brackets, position at the end`() {
        assertDeleteRange(
            "1+(2+3)",
            TextRange(6, 6),
            TextRange(3, 6),
        )
    }

    @Test
    fun `test brackets, position at the start`() {
        assertDeleteRange(
            "1+(2+3)",
            TextRange(3, 3),
            TextRange(3, 6),
        )
    }

    @Test
    fun `test brackets, but position outside`() {
        assertDeleteRange(
            "1+(2+3)",
            TextRange(1, 1),
            TextRange(0, 1),
        )
    }

    @Test
    fun `test brackets, but cursor outside right of it`() {
        assertDeleteRange(
            "1+(2+3)54",
            TextRange(8, 8),
            TextRange(7, 8),
        )
    }

    @Test
    fun `test brackets, but cursor outside right of it, should go to the operator`() {
        assertDeleteRange(
            "1+(2+3)×54",
            TextRange(9, 9),
            TextRange(7, 9),
        )
    }

    @Test
    fun `test nested brackets, inside the nested one`() {
        assertDeleteRange(
            "1+(2+(3+4))",
            TextRange(8, 8),
            TextRange(6, 8),
        )
    }

    @Test
    fun `test nested brackets, inside the outside one`() {
        assertDeleteRange(
            "1+(2+(3+4))",
            TextRange(4, 4),
            TextRange(3, 4),
        )
    }

    @Test
    fun `test nested brackets, inside the outside one, at edge`() {
        assertDeleteRange(
            "1+(2+(3+4))",
            TextRange(3, 3),
            TextRange(3, 10),
        )
    }

    @Test
    fun `test nested empty brackets`() {
        assertDeleteRange(
            "1+(2+(6))",
            TextRange(3, 3),
            TextRange(3, 8),
        )
    }

    @Test
    fun `test empty string`() {
        assertDeleteRange(
            "",
            TextRange(0, 0),
            TextRange(0, 0),
        )
    }

    @Test
    fun `test single character with cursor ar 0,0`() {
        assertDeleteRange(
            "1",
            TextRange(0, 0),
            TextRange(0, 0),
        )
    }

    @Test
    fun `test right bracket cursor after it`() {
        assertDeleteRange(
            "5+(2+6)+4",
            TextRange(8, 8),
            TextRange(7, 8),
        )
    }

    @Test
    fun `test right bracket cursor at edge`() {
        assertDeleteRange(
            "5+(2+6)+4",
            TextRange(7, 7),
            TextRange(3, 7),
        )
    }

    @Test
    fun `test single bracket with cursor at 0,0`() {
        assertDeleteRange(
            "(",
            TextRange(0, 0),
            TextRange(0, 0),
        )
    }

    @Test
    fun `test nested brackets with operators`() {
        assertDeleteRange(
            "1+(2*(3+4))",
            TextRange(6, 6),
            TextRange(6, 9),
        )
    }

    @Test
    fun `test selection range spanning multiple tokens`() {
        assertDeleteRange(
            "1+(2+3)*4",
            TextRange(2, 6),
            TextRange(2, 6),
        )
    }

    @Test
    fun `test empty brackets deletes rest`() {
        assertDeleteRange(
            "5+(",
            TextRange(3, 3),
            TextRange(0, 3),
        )
    }

    @Test
    fun `left bracket, no right bracket`() {
        assertDeleteRange(
            "1+(5+6+",
            TextRange(8, 8),
            TextRange(3, 8),
        )
    }

    @Test
    fun `no bracket`() {
        assertDeleteRange(
            "1+5+6",
            TextRange(5, 5),
            TextRange(0, 5),
        )
    }

    @Test
    fun `closed bracket before, but left bracket after`() {
        assertDeleteRange(
            "1+(5+6)+(5+",
            TextRange(11, 11),
            TextRange(9, 11),
        )
    }


    private fun assertDeleteRange(input: String, selection: TextRange, expected: TextRange) {
        val smartDeleteHandler =
            SmartDeleteHandler(input, selection)
        val actual = smartDeleteHandler.calculateDeleteRange()
        assert(expected == actual) {
            "Expected: $expected, actual: $actual"
        }
    }
}