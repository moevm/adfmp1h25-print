package ru.moevm.printhubapp.presentation.printhub

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.moevm.printhubapp.R
import ru.moevm.printhubapp.ui.theme.AppTheme

@Composable
fun StatisticScreen() {
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppTheme.colors.orange10)
                    .padding(top = 32.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            modifier = Modifier.clickable { },
                            painter = painterResource(R.drawable.back_arrow_ic),
                            contentDescription = null,
                            tint = AppTheme.colors.black9
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = String.format(
                                stringResource(R.string.statistic_screen_title),
                            ),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.colors.black9
                        )
                    }
                    Icon(
                        modifier = Modifier.clickable { },
                        painter = painterResource(R.drawable.info_ic),
                        contentDescription = null,
                        tint = AppTheme.colors.black9
                    )
                }
                HorizontalDivider(
                    color = AppTheme.colors.black9
                )
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .background(AppTheme.colors.gray1, RoundedCornerShape(16.dp))
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            InfoRow(
                titleId = R.string.list_title,
                isList = true
            )
            InfoRow(
                titleId = R.string.toner_title,
                param = "20"
            )
            InfoRow(
                titleId = R.string.profit_title,
                param = "1000"
            )
            InfoRow(
                titleId = R.string.price_printer_title,
            )
        }
    }
}


@Composable
private fun InfoRow(
    titleId: Int,
    param: String = "",
    color: Color = AppTheme.colors.black9,
    isList: Boolean = false
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = String.format(stringResource(titleId), param),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = color
            )
        }
        if(isList) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    text = "A1: 10 штук",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = color
                )
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    text = "A2: 10 штук",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = color
                )
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    text = "A3: 10 штук",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = color
                )
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    text = "A4: 10 штук",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = color
                )
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    text = "A5: 10 штук",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = color
                )
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    text = "A6: 10 штук",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = color
                )
            }
            Spacer(Modifier.height(16.dp))
        }
        HorizontalDivider(color = AppTheme.colors.black9)
    }
}

@Preview(showBackground = true)
@Composable
private fun StatisticScreenPreview() {
    StatisticScreen()
}