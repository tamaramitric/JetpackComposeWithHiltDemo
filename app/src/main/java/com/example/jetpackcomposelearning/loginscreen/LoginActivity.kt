package com.example.jetpackcomposelearning.loginscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackcomposelearning.listscreen.ListActivity
import com.example.jetpackcomposelearning.ui.JetpackComposeLearningTheme

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        setContent {
            JetpackComposeLearningTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    LoginComponent(viewModel)
                }
            }
        }
    }

    private val defaultSpacerSize = 16.dp

    @Composable
    fun LoginComponent(viewModel: LoginViewModel) {
        val email: String by viewModel.email.observeAsState("")
        val password: String by viewModel.password.observeAsState("")
        val isValidEmail =  remember { mutableStateOf(true) }
        val isValidPassword = remember { mutableStateOf(true) }

        val textFieldModifier = Modifier
            .fillMaxWidth()
            .padding(start = defaultSpacerSize, end = defaultSpacerSize)

        Column {
            Text(text = "Welcome!", Modifier.padding(top = 50.dp, start = defaultSpacerSize, end = defaultSpacerSize, bottom = 50.dp).fillMaxWidth().wrapContentSize(
                Alignment.Center), style = MaterialTheme.typography.h3)
            Text(text = "Email:", modifier = Modifier.padding(top = defaultSpacerSize, start = defaultSpacerSize, end = defaultSpacerSize))
            OutlinedTextField(value = email, onValueChange = {viewModel.onEmailChanged(it)},
                modifier = textFieldModifier,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                onImeActionPerformed = { _, softwareController ->
                    softwareController?.hideSoftwareKeyboard()
                },
                isErrorValue = !isValidEmail.value
            )
            Text(text = "Password:", modifier = Modifier.padding(top = defaultSpacerSize, start = defaultSpacerSize, end = defaultSpacerSize))
            OutlinedTextField(value = password, onValueChange = {viewModel.onPasswordChanged(it)},
                modifier = textFieldModifier,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                onImeActionPerformed = { _, softwareController ->
                    softwareController?.hideSoftwareKeyboard()
                },
                isErrorValue = !isValidPassword.value
            )
            Button(
                onClick = {
                    when {
                        viewModel.email.value!!.isEmpty() -> {
                            isValidEmail.value = false
                        }
                        viewModel.password.value!!.isEmpty() -> {
                            isValidPassword.value = false
                        }
                        else -> {
                            isValidEmail.value = true
                            isValidPassword.value = true
                            startActivity(Intent(this@LoginActivity, ListActivity::class.java))
                            finish()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(start = defaultSpacerSize, end = defaultSpacerSize, top = defaultSpacerSize)
            ) {
                Text(text = "Login", modifier = Modifier.padding(all = 8.dp), style = TextStyle(Color.White))
            }
        }
    }
}