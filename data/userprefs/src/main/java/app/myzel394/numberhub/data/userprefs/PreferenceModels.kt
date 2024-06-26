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

package app.myzel394.numberhub.data.userprefs

import app.myzel394.numberhub.core.base.FormatterSymbols
import app.myzel394.numberhub.data.model.converter.UnitGroup
import app.myzel394.numberhub.data.model.converter.UnitsListSorting
import app.myzel394.numberhub.data.model.userprefs.AboutPreferences
import app.myzel394.numberhub.data.model.userprefs.AddSubtractPreferences
import app.myzel394.numberhub.data.model.userprefs.AppPreferences
import app.myzel394.numberhub.data.model.userprefs.BodyMassPreferences
import app.myzel394.numberhub.data.model.userprefs.CalculatorPreferences
import app.myzel394.numberhub.data.model.userprefs.ConverterPreferences
import app.myzel394.numberhub.data.model.userprefs.DisplayPreferences
import app.myzel394.numberhub.data.model.userprefs.FormattingPreferences
import app.myzel394.numberhub.data.model.userprefs.GeneralPreferences
import app.myzel394.numberhub.data.model.userprefs.StartingScreenPreferences
import app.myzel394.numberhub.data.model.userprefs.UnitGroupsPreferences
import io.github.sadellie.themmo.core.MonetMode
import io.github.sadellie.themmo.core.ThemingMode

data class AppPreferencesImpl(
    override val themingMode: ThemingMode,
    override val enableDynamicTheme: Boolean,
    override val enableAmoledTheme: Boolean,
    override val customColor: Long,
    override val monetMode: MonetMode,
    override val startingScreen: String,
    override val enableToolsExperiment: Boolean,
    override val systemFont: Boolean,
    override val enableVibrations: Boolean,
) : AppPreferences

data class GeneralPreferencesImpl(
    override val lastReadChangelog: String,
    override val enableVibrations: Boolean,
    override val hasSeenNewAppAnnouncement: Boolean,
) : GeneralPreferences

data class CalculatorPreferencesImpl(
    override val radianMode: Boolean,
    override val formatterSymbols: FormatterSymbols,
    override val middleZero: Boolean,
    override val acButton: Boolean,
    override val additionalButtons: Boolean,
    override val inverseMode: Boolean,
    override val partialHistoryView: Boolean,
    override val precision: Int,
    override val outputFormat: Int,
) : CalculatorPreferences

data class ConverterPreferencesImpl(
    override val formatterSymbols: FormatterSymbols,
    override val middleZero: Boolean,
    override val acButton: Boolean,
    override val precision: Int,
    override val outputFormat: Int,
    override val unitConverterFormatTime: Boolean,
    override val unitConverterSorting: UnitsListSorting,
    override val shownUnitGroups: List<UnitGroup>,
    override val unitConverterFavoritesOnly: Boolean,
    override val enableToolsExperiment: Boolean,
    override val latestLeftSideUnit: String,
    override val latestRightSideUnit: String,
) : ConverterPreferences

data class DisplayPreferencesImpl(
    override val systemFont: Boolean,
    override val middleZero: Boolean,
    override val acButton: Boolean,
) : DisplayPreferences

data class FormattingPreferencesImpl(
    override val digitsPrecision: Int,
    override val formatterSymbols: FormatterSymbols,
    override val outputFormat: Int,
) : FormattingPreferences

data class UnitGroupsPreferencesImpl(
    override val shownUnitGroups: List<UnitGroup> = UnitGroup.entries,
) : UnitGroupsPreferences

data class AddSubtractPreferencesImpl(
    override val formatterSymbols: FormatterSymbols,
) : AddSubtractPreferences

data class BodyMassPreferencesImpl(
    override val formatterSymbols: FormatterSymbols,
) : BodyMassPreferences

data class AboutPreferencesImpl(
    override val enableToolsExperiment: Boolean,
) : AboutPreferences

data class StartingScreenPreferencesImpl(
    override val startingScreen: String,
) : StartingScreenPreferences
