package com.mapbox.memleak

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mapbox.memleak.ui.theme.MapboxMemLeakTheme

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContent {
         MapboxMemLeakTheme {
            // A surface container using the 'background' color from the theme
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
               Column(
                  horizontalAlignment = Alignment.CenterHorizontally,
                  verticalArrangement = Arrangement.Center,
                  modifier = Modifier.padding(start = 20.dp, end = 20.dp)
               ) {

               }
            }
         }
      }
   }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
   Text(
      text = "Hello $name!",
      modifier = modifier
   )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
   MapboxMemLeakTheme {
      Greeting("Android")
   }
}