package com.example.do_an_co_so_3.presentation.singup_screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.do_an_co_so_3.R
import com.example.do_an_co_so_3.domain.model.Users
import com.example.do_an_co_so_3.navigation.Screens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel()
) {

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordconfirm by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.signUpState.collectAsState(initial = null)

    var isShowPassword by remember {
        mutableStateOf(false)
    }
    var isShowPasswordConfirm by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Đăng ký",
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            label = { Text(text = "Tên tài khoản") },
            placeholder = { Text(text = "Tên tài khoản") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            label = { Text(text = "Email") },
            placeholder = { Text(text = "Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            label = { Text(text = "Password") },
            placeholder = { Text(text = "Password") },
            trailingIcon = {
                IconButton(onClick = {
                    isShowPassword = !isShowPassword
                }) {
                    Icon(
                        if (isShowPassword) painterResource(id = R.drawable.view_709612) else painterResource(id = R.drawable.eye_13949475),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }
            },
            visualTransformation = if (isShowPassword) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = passwordconfirm,
            onValueChange = { passwordconfirm = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            label = { Text(text = "Password") },
            placeholder = { Text(text = "Password") },
            trailingIcon = {
                IconButton(onClick = {
                    isShowPasswordConfirm = !isShowPasswordConfirm
                }) {
                    Icon(
                        if (isShowPasswordConfirm) painterResource(id = R.drawable.view_709612) else painterResource(id = R.drawable.eye_13949475),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }
            },
            visualTransformation = if (isShowPasswordConfirm) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (password == passwordconfirm) {
                    scope.launch {
                        viewModel.registerUser(email, password, username)
                    }
                }
                else if (username.isEmpty() || email.isEmpty() || password.isEmpty() || passwordconfirm.isEmpty()) {
                    Toast.makeText(context, "Không được để trống thông tin", Toast.LENGTH_LONG).show()
                }
                else {
                    Toast.makeText(context, "Mật khẩu không trùng khớp", Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 30.dp, end = 30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(
                text = "Đăng ký",
                color = Color.White,
                modifier = Modifier.padding(7.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            if (state.value?.isLoading == true) {
                CircularProgressIndicator()
            }
        }
        Row(
            modifier = Modifier
            .padding(15.dp)
        ) {
            Text(
                text = "Bạn đã có tài khoản?",
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                modifier = Modifier
                    .clickable {
                        navController.navigate(Screens.SignInScreen.route)
                    },
                text = " Đăng nhập",
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )

        }

    }

    LaunchedEffect(key1 = state.value?.isSuccess) {
        scope.launch {
            if (state.value?.isSuccess?.isNotEmpty() == true) {
                val success = state.value?.isSuccess

                Toast.makeText(context, "$success", Toast.LENGTH_LONG).show()
                email = ""
                password = ""
                passwordconfirm = ""
                username = ""
            }
        }
    }
    LaunchedEffect(key1 = state.value?.isError) {
        scope.launch {
            if (state.value?.isError?.isNotBlank() == true) {
                val error = state.value?.isError
                Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
            }
        }
    }
}


