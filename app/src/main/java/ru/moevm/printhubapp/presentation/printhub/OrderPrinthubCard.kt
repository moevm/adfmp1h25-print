package ru.moevm.printhubapp.presentation.printhub

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.moevm.printhubapp.R
import ru.moevm.printhubapp.ui.theme.AppTheme

@Composable
fun OrderPrinthubCard(
    openDetails: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { openDetails() },
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colors.orange3 ,
            contentColor = AppTheme.colors.black9,
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                modifier = Modifier
                    .background(AppTheme.colors.gray7, RoundedCornerShape(10.dp))
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                text = stringResource(R.string.status_new),
                fontSize = 16.sp,
                color = Color.White
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.order_number),
                fontSize = 16.sp,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.date_of_create),
                fontSize = 16.sp,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.date_of_last_change),
                fontSize = 16.sp,
            )
        }
    }
}

@Preview
@Composable
private fun OrderPrinthubCardPreview() {
    OrderPrinthubCard({})
}