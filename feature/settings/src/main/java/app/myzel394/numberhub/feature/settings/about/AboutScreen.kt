/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2024 Elshan Agaev
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

package app.myzel394.numberhub.feature.settings.about

import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Copyright
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import app.myzel394.numberhub.core.base.BuildConfig
import app.myzel394.numberhub.core.base.R
import app.myzel394.numberhub.core.ui.common.ListItem
import app.myzel394.numberhub.core.ui.common.NavigateUpButton
import app.myzel394.numberhub.core.ui.common.ScaffoldWithLargeTopBar
import app.myzel394.numberhub.core.ui.openLink

@Composable
internal fun AboutRoute(
    navigateUpAction: () -> Unit,
    navigateToThirdParty: () -> Unit,
    navigateToEasterEgg: () -> Unit,
) {
    AboutScreen(
        navigateUpAction = navigateUpAction,
        navigateToThirdParty = navigateToThirdParty,
        navigateToEasterEgg = navigateToEasterEgg,
    )
}

@Composable
private fun AboutScreen(
    navigateUpAction: () -> Unit,
    navigateToThirdParty: () -> Unit,
    navigateToEasterEgg: () -> Unit,
) {
    val mContext = LocalContext.current
    var aboutItemClick: Int by rememberSaveable { mutableIntStateOf(0) }
    var showDialog: Boolean by rememberSaveable { mutableStateOf(false) }

    ScaffoldWithLargeTopBar(
        title = stringResource(R.string.settings_about_unitto),
        navigationIcon = { NavigateUpButton(navigateUpAction) },
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            // CURRENCY RATE NOTE
            item {
                ListItem(
                    icon = Icons.AutoMirrored.Filled.Help,
                    headlineText = stringResource(R.string.settings_currency_rates_note_title),
                    modifier = Modifier.clickable { showDialog = true },
                )
            }

            // TERMS AND CONDITIONS
            item {
                ListItem(
                    icon = Icons.Default.PrivacyTip,
                    headlineText = stringResource(R.string.settings_terms_and_conditions),
                    modifier = Modifier.clickable {
                        openLink(
                            mContext,
                            "https://sadellie.github.io/unitto/terms",
                        )
                    },
                )
            }

            // PRIVACY POLICY
            item {
                ListItem(
                    icon = Icons.Default.Policy,
                    headlineText = stringResource(R.string.settings_privacy_policy),
                    modifier = Modifier.clickable {
                        openLink(
                            mContext,
                            "https://sadellie.github.io/unitto/privacy",
                        )
                    },
                )
            }

            // OPEN SOURCE
            item {
                ListItem(
                    icon = Icons.Default.Code,
                    headlineText = stringResource(R.string.settings_view_source_code),
                    modifier = Modifier.clickable {
                        openLink(
                            mContext,
                            "https://github.com/Myzel394/NumberHub",
                        )
                    },
                )
            }

            // THIRD PARTY
            item {
                ListItem(
                    icon = Icons.Default.Copyright,
                    headlineText = stringResource(R.string.settings_third_party_licenses),
                    modifier = Modifier.clickable { navigateToThirdParty() },
                )
            }

            // APP VERSION
            item {
                ListItem(
                    icon = Icons.Default.Info,
                    headlineText = stringResource(R.string.settings_version_name),
                    supportingText = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})",
                    modifier = Modifier.combinedClickable {
                        aboutItemClick++
                        if (aboutItemClick > 5) {
                            aboutItemClick = 0
                            navigateToEasterEgg()
                        }
                    },
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            title = {
                Text(stringResource(R.string.settings_note))
            },
            text = {
                Text(stringResource(R.string.settings_currency_rates_note_text))
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.ok_label))
                }
            },
            onDismissRequest = { showDialog = false },
        )
    }
}

@Preview
@Composable
fun PreviewAboutScreen() {
    AboutScreen(
        navigateUpAction = {},
        navigateToThirdParty = {},
        navigateToEasterEgg = {},
    )
}
