package com.baileytye.carttest.ui.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.baileytye.carttest.domain.CartItemUI
import com.baileytye.carttest.ui.StoreResponseContent
import com.dropbox.android.external.store4.ResponseOrigin
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreResponse

@Composable
fun CartDestination() {
    CartScreen()
}

@Composable
fun CartScreen(viewModel: CartViewModel = viewModel()) {
    val viewState by viewModel.state.collectAsState()
    CartContent(viewState = viewState, actioner = viewModel::submitAction)
}

@Composable
fun CartContent(viewState: CartViewState, actioner: (CartAction) -> Unit) {
    Scaffold(
        topBar = {
            Surface(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Cart",
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        bottomBar = {
            if (viewState.response is StoreResponse.Data) {
                Surface(modifier = Modifier.fillMaxWidth()) {
                    Button(modifier = Modifier.padding(16.dp), onClick = { /*TODO*/ }) {
                        Text(text = "Order now")
                    }
                }
            }
        }
    ) {
        StoreResponseContent(response = viewState.response, retry = {
            actioner(CartAction.Retry)
        }) { data ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                data.forEach {
                    CartItem(
                        item = it,
                        onUpdateCount = { id, count -> actioner(CartAction.UpdateCart(id, count)) })
                }
                Divider()
                Totals(
                    subTotal = viewState.subTotal,
                    tax = viewState.tax,
                    total = viewState.total
                )
            }
        }

    }
}

@Composable
fun CartItem(
    modifier: Modifier = Modifier,
    item: CartItemUI,
    onUpdateCount: (id: String, count: Int) -> Unit
) {
    Row(modifier = modifier) {
        IconButton(onClick = { onUpdateCount(item.id, (item.count - 1).coerceAtLeast(0)) }) {
            Icon(imageVector = Icons.Filled.Remove, contentDescription = null)
        }
        Text(text = item.count.toString())
        IconButton(onClick = { onUpdateCount(item.id, item.count + 1) }) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = null)
        }
        Text(text = item.name)
        Text(text = item.price)
    }
}

@Composable
fun Totals(subTotal: String, tax: String, total: String) {
    Row {
        Text(text = "Sub-Total")
        Text(text = subTotal)
    }
    Row {
        Text(text = "Tax")
        Text(text = tax)
    }
    Row {
        Text(text = "Total")
        Text(text = total)
    }
}

@Preview
@Composable
fun CartPreview() {
    CartContent(
        viewState = CartViewState(
            response = StoreResponse.Data(
                value = listOf(
                    CartItemUI(
                        id = "ashf",
                        count = 2,
                        name = "Pizza",
                        price = "$3.00",
                        priceValue = 1.50.toBigDecimal()
                    )
                ),
                origin = ResponseOrigin.Fetcher
            )
        )
    ) {}
}