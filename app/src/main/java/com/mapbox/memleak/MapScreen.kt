package com.mapbox.memleak

import android.annotation.SuppressLint
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
import com.mapbox.maps.ImageHolder
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.locationcomponent.location

@SuppressLint("IncorrectNumberOfArgumentsInExpression")
@OptIn(MapboxExperimental::class)
@Composable
fun MapScreen(navigate: () -> Unit) {
   Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier.padding(start = 20.dp, end = 20.dp)
   ) {

      MapboxMap(
         modifier = Modifier.fillMaxWidth().height(200.dp)
      ) {
         MapEffect(Unit) { mapView ->
            // enable location tracking
            mapView.location.enabled = true
            mapView.location.puckBearingEnabled = true
            mapView.location.locationPuck = LocationPuck2D(
               topImage = ImageHolder.from(R.drawable.mapbox_user_icon),
               bearingImage = ImageHolder.from(R.drawable.mapbox_user_bearing_icon),
               shadowImage = ImageHolder.from(R.drawable.mapbox_user_stroke_icon),
               scaleExpression = Expression.interpolate {
                  linear()
                  zoom()
                  stop {
                     literal(0.0)
                     literal(0.6)
                  }
                  stop {
                     literal(20.0)
                     literal(1.0)
                  }
               }.toJson()
            )
         }
      }

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