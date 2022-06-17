package com.sadellie.unitto.screens.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.R
import com.sadellie.unitto.data.LEFT_LIST_SCREEN
import com.sadellie.unitto.data.RIGHT_LIST_SCREEN
import com.sadellie.unitto.data.units.AbstractUnit

/**
 * Top of the main screen. Contains input and output TextFields, and unit selection row of buttons.
 * It's a separate composable, so that we support album orientation (this element will be on the left)
 *
 * @param modifier Modifier that is applied to Column
 * @param inputValue Current input value (like big decimal)
 * @param outputValue Current output value (like big decimal)
 * @param unitFrom [AbstractUnit] on the left
 * @param unitTo [AbstractUnit] on the right
 * @param loadingDatabase Are we still loading units usage stats from database? Disables unit
 * selection buttons.
 * @param loadingNetwork Are we loading data from network? Shows loading text in TextFields
 * @param networkError Did we got errors while trying to get data from network
 * @param onUnitSelectionClick Function that is called when clicking unit selection buttons
 * @param swapUnits Method to swap units
 */
@Composable
fun TopScreenPart(
    modifier: Modifier,
    inputValue: String,
    outputValue: String,
    unitFrom: AbstractUnit,
    unitTo: AbstractUnit,
    loadingDatabase: Boolean,
    loadingNetwork: Boolean,
    networkError: Boolean,
    onUnitSelectionClick: (String) -> Unit,
    swapUnits: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        MyTextField(
            Modifier.fillMaxWidth(),
            inputValue,
            stringResource(id = if (loadingDatabase) R.string.loading_label else unitFrom.shortName),
            loadingNetwork,
            networkError
        )
        MyTextField(
            Modifier.fillMaxWidth(),
            outputValue,
            stringResource(id = if (loadingDatabase) R.string.loading_label else unitTo.shortName),
            loadingNetwork,
            networkError
        )
        // Unit selection buttons
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.Bottom,
        ) {
            UnitSelectionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onClick = { onUnitSelectionClick(LEFT_LIST_SCREEN) },
                label = unitFrom.displayName,
                isLoading = loadingDatabase
            )
            IconButton({ swapUnits() }, enabled = !loadingDatabase) {
                Icon(
                    Icons.Outlined.SwapHoriz, contentDescription = stringResource(
                        id = R.string.swap_units_description
                    )
                )
            }
            UnitSelectionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onClick = { onUnitSelectionClick(RIGHT_LIST_SCREEN) },
                label = unitTo.displayName,
                isLoading = loadingDatabase
            )
        }
    }
}
