package ru.moevm.printhubapp.presentation.printhub

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.moevm.printhubapp.R
import ru.moevm.printhubapp.ui.theme.AppTheme

@Composable
fun OrderDetailsPrinthubScreen(
    idOrder: Int,
) {
    val isReject = false
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
                                stringResource(R.string.order_details_title),
                                idOrder
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
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Статус заказа",
                        fontSize = 16.sp,
                        color = AppTheme.colors.gray7
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        modifier = Modifier
                            .background(AppTheme.colors.gray7, RoundedCornerShape(10.dp))
                            .padding(vertical = 4.dp, horizontal = 8.dp),
                        text = stringResource(R.string.status_new),
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                DateOrderCard()
                DetailsRow(
                    titleId = R.string.format_print,
                    parameter = "A1"
                )
                DetailsRow(
                    titleId = R.string.count_list_with_param,
                    parameter = "1"
                )
                DetailsRow(
                    titleId = R.string.file,
                    parameter = ""
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier
                        .align(Alignment.End)
                        .background(AppTheme.colors.orange3, RoundedCornerShape(16.dp))
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                    text = String.format(stringResource(R.string.total_price), 90),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.colors.black9
                )
                Spacer(Modifier.height(16.dp))
                Comment(
                    titleId = R.string.placeholder_comment,
                    comment = "Хочу быстрее"
                )
                if (isReject) {
                    Spacer(Modifier.height(16.dp))
                    Comment(
                        titleId = R.string.reason_reject,
                        comment = "Кончилась бумага"
                    )
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {  },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colors.orange10,
                        contentColor = AppTheme.colors.black9,
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = stringResource(R.string.take_order_button_text),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = {  },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colors.gray2,
                        contentColor = AppTheme.colors.black9
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = stringResource(R.string.reject_button),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailsRow(
    titleId: Int,
    parameter: String
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        enabled = false,
        onClick = {},
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            disabledContentColor = AppTheme.colors.gray7,
            disabledContainerColor = AppTheme.colors.gray1,
        ),
        border = BorderStroke(1.dp, AppTheme.colors.gray7)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(titleId),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = AppTheme.colors.black9
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = parameter,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.colors.black9
            )
        }
    }
}

@Composable
private fun Comment(
    titleId: Int,
    comment: String,

    ) {
    Column {
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            text = stringResource(titleId),
            fontSize = 16.sp,
            color = AppTheme.colors.gray7
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            value = comment,
            readOnly = true,
            onValueChange = {},
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = AppTheme.colors.black9
            ),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = AppTheme.colors.gray1,
                focusedContainerColor = AppTheme.colors.gray1,
                disabledContainerColor = AppTheme.colors.gray1,
                disabledBorderColor = AppTheme.colors.gray7,
                unfocusedBorderColor = AppTheme.colors.gray7,
                focusedBorderColor = AppTheme.colors.gray7
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderDetailsPrinthubScreenPreview() {
    OrderDetailsPrinthubScreen(idOrder = 1)
}