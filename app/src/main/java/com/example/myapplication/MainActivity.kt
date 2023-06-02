package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foregroundservice_jetpackcompose.utils.foregroundStartService
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.Purple500
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
//                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//                    MyApp()
//                }
                Surface(color = MaterialTheme.colorScheme.background) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Purple500)
                                .padding(15.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Foreground Service",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }

                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = { foregroundStartService("Start") },
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(45.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            ) {
                                Text(
                                    text = "Trigger",
                                    color = Color.White,
                                    fontSize = 15.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(50.dp))

                            Button(
                                onClick = {
                                    foregroundStartService("Exit")
                                },
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(45.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            ) {
                                Text(
                                    text = "Exit",
                                    color = Color.White,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MyApp(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            MyApplicationTheme {
                LoginScreen { navController.navigate("users") }
            }
        }
        composable("users") { UsersScreen() }
    }
}

@Composable
fun LoginScreen(navigateToUsers: (() -> Unit)?) {
    val username = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val loading = remember {
        mutableStateOf(false)
    };

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            Text(text = "NYSNO Login")
            TextField(
                label = { Text("Username") },
                value = username.value,
                onValueChange = { newText ->
                    username.value = newText
                }
            )
            TextField(
                label = { Text("Password") },
                value = password.value,
                onValueChange = { password.value = it },
                visualTransformation = PasswordVisualTransformation()
            )
            AnimatedVisibility(visible = loading.value) {
                CircularProgressIndicator()
            }
            Button(
                onClick = {
                    loading.value = true
                    Handler(Looper.getMainLooper()).postDelayed(Runnable {
                        loading.value = false
                        navigateToUsers?.invoke()
                    }, 3000)
                },
            ) {
                Text(text = "Login")
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun UsersScreen(){
    val list = mutableListOf<String>("Abigail",
            "Alexandra",
            "Alison",
            "Amanda",
            "Amelia", "Abigail",
        "Alexandra",
        "Alison",
        "Amanda",
        "Amelia",
        "Abigail",
        "Alexandra",
        "Alison",
        "Amanda",
        "Amelia", "Abigail",
        "Alexandra",
        "Alison",
        "Amanda",
        "Amelia")
    Surface(modifier = Modifier.fillMaxSize()) {

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)) {
            Text(text = "Users", fontSize = 30.sp)
            list.forEach { message ->
                Text(text = message)
            }
        }
    }
}



@Preview(name = "Full Preview", showSystemUi = true)
@Preview(showBackground = true)
//@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, showSystemUi = true)
@Composable
fun LoginPreview() {
    MyApplicationTheme {
        LoginScreen(null)
    }
}