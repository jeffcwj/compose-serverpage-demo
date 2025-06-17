package compose.serverpage.demo.ui.widget

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MAlertDialog(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    onDismissRequest: () -> Unit = {},
    positiveButtonText: String = "",
    onPositiveButtonClick: () -> Unit = {},
    negativeButtonText: String = "",
    onNegativeButtonClick: () -> Unit = {}
) {
    AlertDialog(
        modifier = modifier,
        title = {
            Text(title)
        },
        text = {
            Text(content)
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            if (positiveButtonText.isNotEmpty()) {
                TextButton (
                    onClick = onPositiveButtonClick
                ) {
                    Text(
                        text = positiveButtonText
                    )
                }
            }
        },
        dismissButton = {
            if (negativeButtonText.isNotEmpty()) {
                TextButton (
                    onClick = onNegativeButtonClick
                ) {
                    Text(
                        text = negativeButtonText
                    )
                }
            }
        }
    )
}

@Composable
fun MCustomAlertDialog(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable () -> Unit,
    onDismissRequest: () -> Unit = {},
    positiveButtonText: String = "",
    onPositiveButtonClick: () -> Unit = {},
    negativeButtonText: String = "",
    onNegativeButtonClick: () -> Unit = {}
) {
    AlertDialog(
        modifier = modifier,
        title = {
            Text(title)
        },
        text = content,
        onDismissRequest = onDismissRequest,
        confirmButton = {
            if (positiveButtonText.isNotEmpty()) {
                TextButton (
                    onClick = onPositiveButtonClick
                ) {
                    Text(
                        text = positiveButtonText
                    )
                }
            }
        },
        dismissButton = {
            if (negativeButtonText.isNotEmpty()) {
                TextButton (
                    onClick = onNegativeButtonClick
                ) {
                    Text(
                        text = negativeButtonText
                    )
                }
            }
        }
    )
}