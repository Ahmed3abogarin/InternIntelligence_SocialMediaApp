package com.ahmed.instagramclone.presentation.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmed.instagramclone.R
import com.ahmed.instagramclone.presentation.components.AppLoading
import com.ahmed.instagramclone.util.Resource

@Composable
fun LoginScreen(
    state: Resource<Unit>?,
    navigateToRegister: () -> Unit,
    event: (LoginEvent) -> Unit,
    navigateToMain: () -> Unit,
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }



    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.p2),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Image(
            painter = painterResource(R.drawable.app_icon),
            contentDescription = "",
            modifier = Modifier
                .size(130.dp)
                .align(Alignment.TopCenter)
                .padding(top = 40.dp)
        )


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.68f)
                .clip(shape = RoundedCornerShape(topStart = 60.dp))
                .background(Color.White)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Login",
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.SemiBold),
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Email",
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 28.dp),
                fontSize = 18.sp
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(14.dp)),
                value = email,
                placeholder = { Text(text = "example@gmail.com") },
                onValueChange = { email = it.trim() },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(34.dp))

            Text(
                text = "Password",
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 28.dp),
                fontSize = 18.sp
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(14.dp)),
                value = password,
                placeholder = { Text(text = "***********") },
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { password = it.trim() },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(44.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(
                    topStart = 12.dp,
                    bottomStart = 12.dp,
                    bottomEnd = 12.dp
                ),
                colors = ButtonDefaults.buttonColors(Color.Black),
                elevation = ButtonDefaults.elevatedButtonElevation(12.dp),
                onClick = {
                    event(LoginEvent.Login(email = email, password = password))
                }) {
                Text("Login", modifier = Modifier.padding(4.dp))
            }
            Spacer(modifier = Modifier.height(25.dp))

            Box(contentAlignment = Alignment.Center) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp
                        ), color = Color.Black
                )

                Text(
                    text = "Or",
                    modifier = Modifier
                        .background(Color.White)
                        .align(Alignment.Center)
                        .padding(horizontal = 6.dp)
                )

            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .padding(horizontal = 32.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    elevation = ButtonDefaults.elevatedButtonElevation(8.dp),
                    colors = ButtonDefaults.buttonColors(Color.White),
                    onClick = {}, shape = RoundedCornerShape(8.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_google),
                        modifier = Modifier.padding(end = 8.dp),
                        contentDescription = ""
                    )
                    Text("Google", color = Color.Black)
                }

                Button(
                    modifier = Modifier.weight(1f),
                    elevation = ButtonDefaults.elevatedButtonElevation(8.dp),
                    colors = ButtonDefaults.buttonColors(Color.White),
                    onClick = {},
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_facebook),
                        modifier = Modifier.padding(end = 8.dp),
                        contentDescription = ""
                    )
                    Text("Facebook", color = Color.Black)
                }

            }

        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(16.dp)
        ) {
            Text(text = "Don't have any account?")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                modifier = Modifier.clickable { navigateToRegister() },
                text = "Sign Up",
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
    when (state) {
        is Resource.Loading -> {
            AppLoading()
        }

        is Resource.Success -> {
            navigateToMain()
        }

        is Resource.Error -> {
            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
        }

        else -> Unit
    }

}
