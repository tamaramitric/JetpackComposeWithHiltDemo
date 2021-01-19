package com.example.jetpackcomposelearning.ui

import android.annotation.SuppressLint
import androidx.compose.animation.ColorPropKey
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.transitionDefinition

val colorPropKey = ColorPropKey()

@SuppressLint("Range")
val ProgressColorTransition = transitionDefinition<Int> {
    state(0){
        this[colorPropKey] = gradient[0]
    }
    state(1){
        this[colorPropKey] = gradient[2]
    }
    transition(fromState = 0, toState = 1){
        colorPropKey using repeatable(
            iterations = AnimationConstants.Infinite,
            animation = keyframes {
                durationMillis = 300
                gradient[1] at 100
            }
        )
    }
}