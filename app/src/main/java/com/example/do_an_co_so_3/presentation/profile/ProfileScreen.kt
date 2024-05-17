package com.example.do_an_co_so_3.presentation.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.do_an_co_so_3.R
import com.example.do_an_co_so_3.di.AuthManager
import com.example.do_an_co_so_3.domain.model.Users
import com.example.do_an_co_so_3.navigation.NavigationGraph
import com.example.do_an_co_so_3.navigation.Screens
import com.example.do_an_co_so_3.presentation.Dimens
import com.example.do_an_co_so_3.ui.theme.Do_an_co_so_3Theme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun ProfileScreen(
    navController: NavController
) {

    var username by rememberSaveable {
        mutableStateOf("")
    }
    val email = AuthManager.getCurrentUserEmail()
    val context = LocalContext.current
    val userId  = Firebase.auth.currentUser?.uid
    userId?.let { uid ->
        Firebase.firestore.collection("User").document(uid).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val user = document.toObject(Users::class.java)
                    if (user != null) {
                        username = user.name.toString()
                    }
                }
            }
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .padding(start = 8.dp, top = 100.dp, end = 8.dp, bottom = 8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = null,
                    modifier = Modifier
                        .size(90.dp)
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(Dimens.MediumPadding1))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tên",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = username,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(Dimens.MediumPadding1))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Email",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "$email",
                    fontSize = 18.sp,
                )
            }
            Spacer(modifier = Modifier.height(Dimens.MediumPadding1))
            Button(
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    Toast.makeText(context, "Đã đăng xuất thành công", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screens.SignInScreen.route)
                },
                modifier = Modifier
                    .padding(top = 15.dp, start = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
            ) {
                Text(
                    text = "Đăng xuất",
                    color = Color.White,
                    modifier = Modifier.padding(7.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewProfile() {
    Do_an_co_so_3Theme {
        NavigationGraph(startDestination = Screens.ProfileScreen.route)
    }
}