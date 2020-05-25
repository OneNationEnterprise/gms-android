package com.gymapp.helper.extensions

import android.util.Patterns
import java.util.regex.Pattern

fun String.isValidEmail() = Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidPassword() = Pattern.matches("\\w{6,20}", this.trim())

fun String.isValidName() = Pattern.matches("\\w{1,20}[A-Za-z]", this.trim())