/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

package com.sadellie.unitto.feature.datedifference.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.DatePickerDialog
import com.sadellie.unitto.core.ui.common.TimePickerDialog
import java.time.ZonedDateTime

@Composable
internal fun DateTimeDialogs(
    dialogState: DialogState?,
    updateDialogState: (DialogState) -> Unit,
    date: ZonedDateTime,
    updateDate: (ZonedDateTime) -> Unit,
    bothState: DialogState,
    timeState: DialogState,
    dateState: DialogState,
) {
    when (dialogState) {
        bothState -> {
            TimePickerDialog(
                hour = date.hour,
                minute = date.minute,
                onDismiss = { updateDialogState(DialogState.NONE) },
                onConfirm = { hour, minute ->
                    updateDate(date.withHour(hour).withMinute(minute))
                    updateDialogState(dateState)
                },
                confirmLabel = stringResource(R.string.next_label),
            )
        }

        timeState -> {
            TimePickerDialog(
                hour = date.hour,
                minute = date.minute,
                onDismiss = { updateDialogState(DialogState.NONE) },
                onConfirm = { hour, minute ->
                    updateDate(date.withHour(hour).withMinute(minute))
                    updateDialogState(DialogState.NONE)
                },
            )
        }

        dateState -> {
            DatePickerDialog(
                localDateTime = date,
                onDismiss = { updateDialogState(DialogState.NONE) },
                onConfirm = {
                    updateDate(it)
                    updateDialogState(DialogState.NONE)
                }
            )
        }

        else -> {}
    }
}

internal enum class DialogState { NONE, FROM, FROM_TIME, FROM_DATE, TO, TO_TIME, TO_DATE }
