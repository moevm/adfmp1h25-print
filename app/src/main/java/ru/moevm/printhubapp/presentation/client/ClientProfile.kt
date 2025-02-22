package ru.moevm.printhubapp.presentation.client

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
fun ClientProfile() {
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppTheme.colors.orange10)
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
                            painter = painterResource(R.drawable.back_arrow_ic),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = stringResource(R.string.client_profile_title),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.colors.black9
                        )
                    }
                    Icon(
                        painter = painterResource(R.drawable.info_ic),
                        contentDescription = null,
                        tint = AppTheme.colors.black9
                    )
                }
                HorizontalDivider(
                    color = AppTheme.colors.black9
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .background(AppTheme.colors.gray1, RoundedCornerShape(16.dp))
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            InfoRow(
                titleId = R.string.name_title,
                param = "Клиент"
            )
            InfoRow(
                titleId = R.string.mail_title,
                param = "test_user@mail.ru"
            )
            InfoRow(
                titleId = R.string.logout,
                isNavigate = true,
                color = AppTheme.colors.red
            )
        }
    }
}

@Composable
private fun InfoRow(
    titleId: Int,
    param: String = "",
    color: Color = AppTheme.colors.black9,
    isNavigate: Boolean = false
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = String.format(stringResource(titleId), param),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = color
            )
            if (isNavigate) {
                Icon(
                    painter = painterResource(R.drawable.right_arrow_ic),
                    contentDescription = null
                )
            }
        }
        HorizontalDivider(color = AppTheme.colors.black9)
    }
}

@Preview(showBackground = true)
@Composable
private fun ClientProfilePreview() {
    ClientProfile()
}