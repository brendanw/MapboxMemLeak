package com.mapbox.memleak

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mapbox.common.MapboxOptions
import com.mapbox.memleak.ui.theme.MapboxMemLeakTheme

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      MapboxOptions.accessToken = BuildConfig.MAPBOX_ACCESS_TOKEN

      setContent {
         MapboxMemLeakTheme {
            // A surface container using the 'background' color from the theme
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
               val activePage = remember { mutableStateOf(Page.Map) }

               when (activePage.value) {
                  Page.Map -> {
                     MapScreen { activePage.value = Page.Exit }
                  }

                  Page.Exit -> {
                     ExitScreen { activePage.value = Page.Map }
                  }
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

enum class Page { Map, Exit }