/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.sadellie.evaluatto

import org.junit.Test

class ExpressionSimpleTest {

    @Test
    fun expression1() = assertExpr("789", "789")

    @Test
    fun expression2() = assertExpr("0.1+0.2", "0.3")

    @Test
    fun expression3() = assertExpr(".1+.2", "0.3")

    @Test
    fun expression4() = assertExpr("789+200", "989")

    @Test
    fun expression5() = assertExpr("600×7.89", "4734")

    @Test
    fun expression6() = assertExpr("600÷7", "85.7142857143")

    @Test
    fun expression7() = assertExpr("(200+200)×200", "80000")

    @Test
    fun expression8() = assertExpr("99^5", "9509900499")

    @Test
    fun expression9() = assertExpr("12!", "479001600")

    @Test
    fun expression10() = assertExpr("12#5", "2")

    @Test
    fun `125 plus 9 percent`() = assertExpr("125+9%", "136.25")

    @Test
    fun expression11() = assertExpr("12×√5", "26.8328157300")

    @Test
    fun expression12() = assertExpr("sin(42)", "-0.9165215479")

    @Test
    fun expression13() = assertExpr("sin(42)", "0.6691306064", radianMode = false)

    @Test
    fun expression14() = assertExpr("cos(42)", "-0.3999853150")

    @Test
    fun expression15() = assertExpr("cos(42)", "0.7431448255", radianMode = false)

    @Test
    fun expression16() = assertExpr("tan(42)", "2.2913879924")

    @Test
    fun expression17() = assertExpr("tan(42)", "0.9004040443", radianMode = false)

    @Test
    fun expression18() = assertExpr("sin⁻¹(.69)", "0.7614890527")

    @Test
    fun expression19() = assertExpr("sin⁻¹(.69)", "43.6301088679", radianMode = false)

    @Test
    fun expression20() = assertExpr("cos⁻¹(.69)", "0.8093072740")

    @Test
    fun expression21() = assertExpr("cos⁻¹(.69)", "46.3698911321", radianMode = false)

    @Test
    fun expression22() = assertExpr("tan⁻¹(.69)", "0.6039829783")

    @Test
    fun expression23() = assertExpr("tan⁻¹(.69)", "34.6056755516", radianMode = false)

    @Test
    fun expression24() = assertExpr("ln(.69)", "-0.3710636814")

    @Test
    fun expression25() = assertExpr("log(.69)", "-0.1611509093")

    @Test
    fun expression26() = assertExpr("exp(3)", "20.0855369232")

    @Test
    fun expression27() = assertExpr("π", "3.1415926536")

    @Test
    fun expression28() = assertExpr("e", "2.7182818285")

    @Test
    fun expression29() = assertExpr("0!", "1")

    @Test
    fun factorial() = assertExpr("5!", "120")

    @Test
    fun singleNumber() = assertExpr("42", "42")

    @Test(expected = NumberFormatException::class)
    fun divisionByZero() = assertExpr("1÷0", "")

    @Test(expected = NumberFormatException::class)
    fun invalidExpressionMissingOperand() = assertExpr("42+", "")

    @Test
    fun largeNumbers() = assertExpr("9999999999*9999999999", "99999999980000000001")

    @Test
    fun highPrecision() = assertExpr("1÷3", "0.3333333333")

    @Test
    fun deeplyNestedParentheses() = assertExpr("((((((42))))))", "42")

    @Test
    fun constantsAndFunctions() = assertExpr("π+e", "${Math.PI + Math.E}")

    @Test(expected = NumberFormatException::class)
    fun edgeMathematicalCases() = assertExpr("0^0", "")

    @Test
    fun zeroExponent() = assertExpr("5^0", "1")

    @Test
    fun negativeExponent() = assertExpr("5^(−2)", "0.04")

    @Test
    fun trigonometricLimits() = assertExpr("sin(0)", "0")

    @Test
    fun sinAtPi() = assertExpr("sin(π)", "0")

    @Test
    fun sinAtHalfPi() = assertExpr("sin(π÷2)", "1")

    @Test
    fun cosAtPi() = assertExpr("cos(π)", "-1")

    @Test
    fun cosAtHalfPi() = assertExpr("cos(π÷2)", "0")

    @Test
    fun cosAtPiOverThree() = assertExpr("cos(π÷3)", "0.5")
}
