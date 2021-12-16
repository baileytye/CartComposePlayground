package com.baileytye.carttest.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baileytye.carttest.R
import com.baileytye.carttest.ui.theme.CartComposePlaygroundTheme
import com.dropbox.android.external.store4.StoreResponse

@Composable
fun <T> StoreResponseContent(
    response: StoreResponse<T>,
    loading: @Composable () -> Unit = { StoreLoadingDefault() },
    empty: @Composable () -> Unit = { StoreEmptyDefault() },
    exception: @Composable (error: Throwable, retry: () -> Unit) -> Unit = { error, retry ->
        StoreErrorDefault(
            error, retry
        )
    },
    messageError: @Composable (message: String, retry: () -> Unit) -> Unit = { error, retry ->
        StoreErrorMessageDefault(errorMessage = error, retry = retry)
    },
    retry: () -> Unit,
    content: @Composable (T) -> Unit,
) {
    when (response) {
        is StoreResponse.Loading -> loading()
        is StoreResponse.Data -> response.dataOrNull()?.let {
            content(it)
        }
        is StoreResponse.NoNewData -> empty()
        is StoreResponse.Error.Exception -> exception(response.error, retry)
        is StoreResponse.Error.Message -> messageError(response.message, retry)
    }
}

@Composable
fun StoreErrorDefault(error: Throwable, retry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .defaultMinSize(minHeight = 150.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
                Text(text = error.localizedMessage ?: "", textAlign = TextAlign.Center)
            }
            Spacer(modifier = Modifier.height(12.dp))
            TextButton(onClick = retry) {
                Text(text = "Retry")
            }
        }
    }
}

@Composable
@Preview
fun StoreErrorDefaultPreview() {
    CartComposePlaygroundTheme() {
        Surface {
            StoreErrorDefault(Exception("Test error")) {}
        }
    }
}

@Composable
fun StoreErrorMessageDefault(errorMessage: String, retry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .defaultMinSize(minHeight = 150.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
                Text(text = errorMessage)
            }
            Spacer(modifier = Modifier.height(12.dp))
            TextButton(onClick = retry) {
                Text(text = "Retry")
            }
        }
    }
}

@Composable
@Preview
fun StoreErrorMessageDefaultPreview() {
    CartComposePlaygroundTheme {
        Surface {
            StoreErrorMessageDefault(errorMessage = "Test message error") {}
        }
    }
}

@Composable
fun StoreLoadingDefault() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .defaultMinSize(minHeight = 150.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            strokeWidth = 1.5.dp,
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
@Preview
fun StoreLoadingDefaultPreview() {
    CartComposePlaygroundTheme {
        Surface {
            StoreLoadingDefault()
        }
    }
}

@Composable
fun StoreEmptyDefault(message: String = "No results") {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .defaultMinSize(minHeight = 150.dp),
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
            Text(text = message)
        }
    }
}

@Composable
@Preview
fun StoreEmptyDefaultPreview() {
    CartComposePlaygroundTheme {
        Surface {
            StoreEmptyDefault()
        }
    }
}