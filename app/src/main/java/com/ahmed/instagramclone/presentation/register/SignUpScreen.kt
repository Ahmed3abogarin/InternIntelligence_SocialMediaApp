package com.ahmed.instagramclone.presentation.register

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahmed.instagramclone.R
import com.ahmed.instagramclone.Resource
import com.ahmed.instagramclone.domain.model.User

@Composable
fun SignUpScreen(navigateUp: () -> Unit, navigateToMain: () -> Unit) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val viewModel: RegisterViewModel = hiltViewModel()
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val state = viewModel.state.value
    val fieldState = viewModel.registerState.value

    if (fieldState.passwordState != null || fieldState.emailState != null) {
        Toast.makeText(context, "Check your inputs", Toast.LENGTH_SHORT).show()
    }
    when (state) {
        is Resource.Loading -> {
            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
        }

        is Resource.Success -> {
            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            navigateToMain()
        }

        is Resource.Error -> {
            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
        }

        else -> Unit
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.p2),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(start = 16.dp, top = 4.dp)
        ) {
            IconButton(
                onClick = { navigateUp() },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(34.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }


            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Sign Up",
                style = MaterialTheme.typography.displaySmall.copy(
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .clip(shape = RoundedCornerShape(topStart = 60.dp))
                .background(Color.White)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(34.dp))

            Text(
                text = "First name",
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
                value = firstName,
                placeholder = { Text(text = "First name") },
                onValueChange = { firstName = it.trim() },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(34.dp))

            Text(
                text = "Last name",
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
                value = lastName,
                placeholder = { Text(text = "Last name") },
                onValueChange = { lastName = it },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(34.dp))

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
                onValueChange = { email = it },
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
                onValueChange = { password = it },
                placeholder = { Text(text = "password") },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(34.dp))

            Text(
                text = "Confirm password",
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
                value = confirmPassword,
                placeholder = { Text(text = "********") },
                onValueChange = { confirmPassword = it },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(36.dp))

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
                    viewModel.createNewUser(
                        user =
                            User(firstName = firstName, lastName = lastName, email = email),
                        password = password,
                        confirmPassword = confirmPassword

                    )
                }) {
                Text("Sign Up", modifier = Modifier.padding(4.dp))
            }

        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(16.dp)
        ) {
            Text(text = "Already have any account?")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                modifier = Modifier.clickable { navigateUp()  },
                text = "Sign In",
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }

}

//@Preview
//@Composable
//fun SignPreview() {
//    InstagramCloneTheme {
//        SignUpScreen()
//    }
//}