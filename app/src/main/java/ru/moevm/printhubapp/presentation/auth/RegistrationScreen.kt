package ru.moevm.printhubapp.presentation.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.moevm.printhubapp.R
import ru.moevm.printhubapp.domain.entity.Role
import ru.moevm.printhubapp.ui.theme.AppTheme

@Composable
fun RegistrationScreen(
    onRegistration: () -> Unit,
    onAbout: () -> Unit
) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var nameCompany by remember { mutableStateOf("") }
    var role by remember { mutableStateOf(Role.INIT) }
    var expanded by remember { mutableStateOf(false) }
    var selectedDropMenu by remember { mutableStateOf(R.string.select_role) }
    var buttonWidth by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 32.dp,
                bottom = 48.dp,
                start = 16.dp,
                end = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(
                painter = painterResource(R.drawable.info_ic),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { onAbout() },
                tint = AppTheme.colors.gray7
            )
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(R.drawable.logo_ic),
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
            Spacer(modifier = Modifier.height(50.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = login,
                onValueChange = {
                    login = it
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.placeholder_mail),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = AppTheme.colors.gray7
                    )
                },
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 20.sp,
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
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = {
                    password = it
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.placeholder_password),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = AppTheme.colors.gray7
                    )
                },
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 20.sp,
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
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            buttonWidth = coordinates.size.width
                        },
                    onClick = { expanded = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colors.gray1,
                        contentColor = AppTheme.colors.gray7,
                    ),
                    border = BorderStroke(1.dp, AppTheme.colors.gray7)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val color = if (selectedDropMenu == R.string.select_role) AppTheme.colors.gray7
                        else AppTheme.colors.black9
                        Text(
                            text = stringResource(selectedDropMenu),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            color = color
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Icon(
                            painter = painterResource(R.drawable.drop_down_arrow_ic),
                            contentDescription = null,
                            tint = color
                        )
                    }
                }
                DropdownMenu(
                    modifier = Modifier
                        .background(AppTheme.colors.gray1, RoundedCornerShape(12.dp))
                        .width(with(LocalDensity.current) { buttonWidth.toDp() }),
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    RegistrationDropDownItem.entries.forEach() {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(it.value),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = AppTheme.colors.black9
                                )
                            },
                            onClick = {
                                selectedDropMenu = it.value
                                role = it.role
                                expanded = false
                            }
                        )
                    }
                }
            }
            if (role == Role.PRINTHUB) {
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = nameCompany,
                    onValueChange = {
                        nameCompany = it
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.placeholder_nameCompany),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            color = AppTheme.colors.gray7
                        )
                    },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 20.sp,
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
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = address,
                    onValueChange = {
                        address = it
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.placeholder_address),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            color = AppTheme.colors.gray7
                        )
                    },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 20.sp,
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

        val isEnable =
            when (role) {
                Role.CLIENT -> (login.isNotEmpty() && password.isNotEmpty())
                Role.PRINTHUB -> (login.isNotEmpty() && password.isNotEmpty() &&
                        nameCompany.isNotEmpty() && address.isNotEmpty())

                else -> false
            }

        Button(
            onClick = { onRegistration() },
            modifier = Modifier.fillMaxWidth(),
            enabled = isEnable,
            colors = ButtonDefaults.buttonColors(
                containerColor = AppTheme.colors.orange10,
                contentColor = AppTheme.colors.black9,
                disabledContainerColor = AppTheme.colors.gray1,
                disabledContentColor = AppTheme.colors.gray7
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(R.string.registration_button_text),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthScreenPreview() {
    RegistrationScreen({}, {})
}