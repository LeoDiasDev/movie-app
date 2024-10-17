package br.com.movieapp.core.presentation.components.common

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.StringRes
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import br.com.movieapp.ui.theme.black
import br.com.movieapp.ui.theme.white

@Composable
fun MovieAppBar(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    textColor: Color = white,
    backgroundColor: Color = black,
    showBackButton: Boolean = false // Recebe o NavController para navegação
    )
{
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    TopAppBar (
        title = {
            Text(text = stringResource(id = title), color = textColor)
        },
        backgroundColor = backgroundColor,
        modifier = modifier,
        navigationIcon = if (showBackButton) {
            {
                IconButton(onClick = { backPressedDispatcher?.onBackPressed() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "")
                }
            }
        } else null
    )
}