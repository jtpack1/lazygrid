package com.example.intei

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.intei.ui.theme.DataState
import com.example.intei.ui.theme.SiswaModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AmbilData : ViewModel() {
    val response: MutableState<DataState> = mutableStateOf(DataState.Empty)
    init {
        AmbilSekolah()
    }

    private fun AmbilSekolah() {
        val db = Firebase.firestore
        val dss = mutableListOf<SiswaModel>()
        response.value = DataState.Loading
        db.collection("sekolah").get().addOnSuccessListener() {

            for (dg in it.documents) {
                val se = dg.toObject(SiswaModel::class.java)
                // Greeting(se.toString())
                //Greeting(se)
                if (se != null)
                    dss.add(se)

            }
            response.value = DataState.Success(dss)
        }
    }
}