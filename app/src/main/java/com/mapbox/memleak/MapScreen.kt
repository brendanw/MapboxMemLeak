package com.mapbox.memleak

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mapbox.maps.extension.compose.MapboxMap

@Composable
fun MapScreen(navigate: () -> Unit) {
   Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier.padding(start = 20.dp, end = 20.dp)
   ) {

      MapboxMap(
         modifier = Modifier.fillMaxWidth().height(200.dp)
      ) {  }

      Spacer(Modifier.height(20.dp))

      Button(
         onClick = {
            navigate()
         }
      ) {
         Text("Navigate away from map")
      }
   }
}