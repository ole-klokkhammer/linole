package com.linole.diefamilie.screens.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.linole.diefamilie.data.Account
import com.linole.diefamilie.data.UserData

class AccountPreviewProvider : PreviewParameterProvider<List<Account>> {
    override val values = sequenceOf(UserData.accounts)
    override val count: Int = values.count()
}

@Preview
@Composable
fun AccountScreen(@PreviewParameter(AccountPreviewProvider::class) accounts: List<Account>) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Card {
            Column(modifier = Modifier.padding(12.dp)) {
                accounts.forEach { account ->
                    AccountRow(
                        name = account.name,
                        number = account.number,
                        amount = account.balance,
                        color = account.color
                    )
                }
            }
        }
    }
}

@Composable
fun AccountRow(name: String, number: Int, amount: Float, color: Color) {
    val typography = MaterialTheme.typography
    Column(Modifier) {
        Text(text = name, style = typography.body1)
    }
}