package com.example.tayapp.presentation.states

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}