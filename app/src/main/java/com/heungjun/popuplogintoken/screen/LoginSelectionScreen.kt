package com.heungjun.popuplogintoken.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginSelectionScreen(onLoginSelected: (String) -> Unit, onSignUpSelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { onLoginSelected("user") }) {
            Text("User Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onLoginSelected("company") }) {
            Text("Company Login")
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { onSignUpSelected("user") }) {
            Text("User Sign Up")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onSignUpSelected("company") }) {
            Text("Company Sign Up")
        }
    }
}