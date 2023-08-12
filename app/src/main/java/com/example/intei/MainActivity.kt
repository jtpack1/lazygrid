package com.example.intei

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.intei.ui.theme.DataState
import com.example.intei.ui.theme.InteiTheme
import com.example.intei.ui.theme.SiswaModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    private val viewModel: AmbilData by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            // A surface container using the 'background' color from the theme
            //Greeting(viewModel = viewModel)
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .border(10.dp, Color.Black),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Greeting(viewModel = viewModel)
                    regis()
                }
            }


        }
    }
}

@Composable
fun Greeting(viewModel: AmbilData) {
    Log.e("string3", "$viewModel")
    when (val result = viewModel.response.value) {
        is DataState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

            }
        }

        is DataState.Success -> {
            Log.e("string4", "${result.data}")
            lazyGridTampil(list = result.data)

        }

        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error Fetching data"
                )
            }
        }
    }


}

@Composable
fun lazyGridTampil(list: MutableList<SiswaModel>) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(list) { nama ->
            Text(text = "${nama.nama}")
            Spacer(modifier = Modifier.padding(20.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun regis() {
    var nimko by remember {
        mutableStateOf("")
    }
    var nameko by remember {
        mutableStateOf("")
    }
    TextField(modifier = Modifier, value = nimko, onValueChange = { nims ->
        nimko = nims
    })
    Spacer(modifier = Modifier.height(50.dp))
    TextField(modifier = Modifier, value = nameko, onValueChange = { names ->
        nameko = names
    })
    val textalert = remember {
        mutableStateOf("State")
    }
    Button(onClick = {
        val fires = Firebase.firestore
        val inhash = hashMapOf(
            "nim" to nimko,
            "nama" to nameko
        )

        fires.collection("sekolah").add(inhash).addOnSuccessListener {
             textalert.value = "Sukses"
        }
    }) {
        Text(text = textalert.value )
    }


}