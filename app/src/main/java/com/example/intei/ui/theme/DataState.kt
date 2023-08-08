package com.example.intei.ui.theme

sealed class DataState{
    class Success(val data: MutableList<SiswaModel>) : DataState()
    class Failure(val message: String) : DataState()
    object Loading : DataState()
    object Empty : DataState()
}
