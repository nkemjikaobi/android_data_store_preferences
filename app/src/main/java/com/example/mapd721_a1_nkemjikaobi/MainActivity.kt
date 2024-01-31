package com.example.mapd721_a1_nkemjikaobi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mapd721_a1_nkemjikaobi.datastore.StoreUserDetails
import com.example.mapd721_a1_nkemjikaobi.ui.theme.MAPD721A1NkemjikaObiTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MAPD721A1NkemjikaObiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    // context
    val context = LocalContext.current

    // scope
    val scope = rememberCoroutineScope()

    // datastore
    val dataStore = StoreUserDetails(context)

    // get saved username, email and password
    val savedUsernameState = dataStore.getUsername.collectAsState(initial = "")
    val savedEmailState = dataStore.getEmail.collectAsState(initial = "")
    val savedPassState = dataStore.getPassword.collectAsState(initial = "")

    var username by remember { mutableStateOf( "") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoaded by remember { mutableStateOf( false ) }

    Column(modifier = Modifier.fillMaxSize()) {

        //Username Label
        Text(
            modifier = Modifier
                .padding(16.dp, top = 30.dp),
            text = "Username",
            color = Color.Gray,
            fontSize = 12.sp
        )

        //Username input field
        OutlinedTextField(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            value = if (isLoaded) savedUsernameState.value ?: "" else username,
            onValueChange = {
                username = it
                isLoaded =  false
                            },
        )

        //Email Label
        Text(
            modifier = Modifier
                .padding(16.dp, top = 30.dp),
            text = "Email",
            color = Color.Gray,
            fontSize = 12.sp
        )

        //Email input field
        OutlinedTextField(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            value = if (isLoaded) savedEmailState.value ?: "" else email,
            onValueChange = { email = it
                isLoaded =  false
            },
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            value = if (isLoaded) savedPassState.value ?: "" else password,
            onValueChange = { password = it
                isLoaded =  false
            },
            label = { Text(text = "Password", color = Color.Gray, fontSize = 12.sp) },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(20.dp))

       Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
           Button(
               modifier = Modifier
                   .padding(start = 16.dp, end = 16.dp),
           onClick = {
                isLoaded = true
               Toast.makeText(context, "Details loaded", Toast.LENGTH_SHORT).show()
           },
           )
           {
               // button text
               Text(
                   text = "Load",
                   color = Color.White,
                   fontSize = 18.sp
               )
           }

           // save button
           Button(
               modifier = Modifier
                   .padding(start = 16.dp, end = 16.dp),
               onClick = {
                   //launch the class in a coroutine scope
                   scope.launch {
                       dataStore.saveDetails(username, email, password)
//                       isLoaded =  true
                       Toast.makeText(context, "Details saved", Toast.LENGTH_SHORT).show()
                   }
               },
           )
           {
               // button text
               Text(
                   text = "Save",
                   color = Color.White,
                   fontSize = 18.sp
               )
           }

           // clear button
           Button(
               modifier = Modifier
                   .padding(start = 16.dp, end = 16.dp),
               onClick = {
                   //launch the class in a coroutine scope
                   scope.launch {
                       dataStore.deleteDetails()
                       isLoaded = false
                       username = ""
                       email = ""
                       password = ""
                       Toast.makeText(context, "Details deleted", Toast.LENGTH_SHORT).show()
                   }
               },
           )
           {
               // button text
               Text(
                   text = "Clear",
                   color = Color.White,
                   fontSize = 18.sp
               )
           }
       }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "Nkemjika Obi",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "301275091",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MAPD721A1NkemjikaObiTheme {
        MainScreen()
    }
}