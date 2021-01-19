package com.example.jetpackcomposelearning.animationscreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.TransitionState
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.core.tween
import androidx.compose.animation.transition
import androidx.compose.foundation.Border
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawOpacity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.jetpackcomposelearning.ui.JetpackComposeLearningTheme
import com.example.jetpackcomposelearning.ui.purple700

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetpackComposeLearningTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    AnimationComponent()
                }
            }
        }
    }

    enum class ButtonState {
        IDLE, PRESSED
    }

    @Preview(
        showDecoration = true
    )
    @Composable
    fun AnimationComponent() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedFavButton()
        }
    }

    @Composable
    fun AnimatedFavButton() {
        val buttonState = remember { mutableStateOf(ButtonState.IDLE) }

        val transitionDefinition = transitionDefinition<ButtonState> {
            state(ButtonState.IDLE) {
                this[width] = 300.dp
                this[roundedCorners] = 6
                this[textColor] = purple700
                this[backgroundColor] = Color.White
            }

            state(ButtonState.PRESSED) {
                this[width] = 60.dp
                this[roundedCorners] = 50
                this[textColor] = Color.White
                this[backgroundColor] = purple700
            }
        }

        val toState = if (buttonState.value == ButtonState.IDLE) {
            ButtonState.PRESSED
        } else {
            ButtonState.IDLE
        }

        val state = transition(
            definition = transitionDefinition,
            initState = buttonState.value,
            toState = toState
        ) {

        }

        Button(
            shape = RoundedCornerShape(state[roundedCorners]),
            border = BorderStroke(1.dp, state[textColor]),
            modifier = Modifier.size(state[width], 60.dp).animateContentSize(
                animSpec = tween(durationMillis = 1500)
            ),
            colors = ButtonConstants.defaultButtonColors(
                backgroundColor = state[backgroundColor]
            ),
            onClick = {
                buttonState.value = if (buttonState.value == ButtonState.IDLE) {
                    ButtonState.PRESSED
                } else {
                    ButtonState.IDLE
                }
            }
        ) {
            ButtonContent(buttonState = buttonState, state = state)
        }
    }

    @Composable
    fun ButtonContent(
        buttonState: MutableState<ButtonState>,
        state: TransitionState
    ) {
        if (buttonState.value == ButtonState.IDLE) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    Modifier.width(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        tint = state[textColor],
                        asset = Icons.Default.FavoriteBorder,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    "ADD TO FAVORITES!",
                    softWrap = false,
                    color = state[textColor]
                )
            }
        } else {
            Icon(
                tint = state[textColor],
                asset = Icons.Default.Favorite,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}