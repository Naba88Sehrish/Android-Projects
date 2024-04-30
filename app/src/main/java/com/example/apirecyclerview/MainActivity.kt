package com.example.apirecyclerview


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.apirecyclerview.ui.theme.APIrecyclerviewTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APIrecyclerviewTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyScreen()
                }
            }
        }
    }
}

@Composable
fun MyScreen() {
    var productList by remember { mutableStateOf(emptyList<MyData.Product>()) }

    LaunchedEffect(Unit) {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        retrofitBuilder.getProductData().enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                val responseBody = response.body()
                productList = responseBody?.products ?: emptyList()
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                // if api call fails
                 Log.d("Main Activity ", "onFailure: " + t.message)
            }
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProductList(productList = productList)
    }
}

@Composable
fun ProductList(productList: List<MyData.Product>) {
    LazyColumn {
        items(productList.size) { index ->
            ProductItem(product = productList[index])
        }
    }
}

@Composable
fun ProductItem(product: MyData.Product) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = product.title, fontSize = 20.sp)
        // You can display other product details here
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    APIrecyclerviewTheme {
        MyScreen()
    }
}
