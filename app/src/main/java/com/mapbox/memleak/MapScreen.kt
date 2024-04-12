package com.mapbox.memleak

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.ImageHolder
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotationGroup
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.viewannotation.annotationAnchor
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions

@SuppressLint("IncorrectNumberOfArgumentsInExpression")
@OptIn(MapboxExperimental::class)
@Composable
fun MapScreen(navigate: () -> Unit) {
   Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier.padding(start = 20.dp, end = 20.dp)
   ) {
      val cameraState = remember {
         MapboxCameraState(
            center = LatLng( 37.746011, -119.533632),
            zoom = 15.5,
            pitch = 0.0,
            bearing = 0.0
         )
      }

      Box {
      MapboxMap(
         modifier = Modifier.fillMaxWidth().height(200.dp),
         scaleBar = {
            ScaleBar(
               modifier = Modifier.align(Alignment.BottomStart)
                  .padding(bottom = 40.dp, start = 10.dp),
               isMetricUnit = true
            )
         },
         compass = {
            Compass(
               modifier = Modifier
                  .padding(top = 60.dp, start = 10.dp)
                  .align(Alignment.TopStart)
            )
         },
         mapInitOptionsFactory = { context ->
            MapInitOptions(
               context = context,
               styleUri = "mapbox://styles/basebeta/clm3fw4kx00tk01qyco2x8f3p",
               cameraOptions = CameraOptions.Builder()
                  .center(
                     Point.fromLngLat(
                        cameraState.center.longitude,
                        cameraState.center.latitude
                     )
                  )
                  .zoom(cameraState.zoom)
                  .pitch(cameraState.pitch)
                  .bearing(cameraState.bearing)
                  .build(),
            )
         }
      ) {
         val isMarkerVisible = remember { mutableStateOf(false) }

         // add pointannotation
         val option = remember { PointAnnotationOptions()
            .withPoint(Point.fromLngLat(-119.533632, 37.746011))
            .withIconImage("blue-pin") }

         PointAnnotationGroup(
            listOf(option),
            onClick = {
               isMarkerVisible.value = true
               true
            }
         )

         ViewAnnotationMarker(isMarkerVisible)

         val context = LocalContext.current

         MapEffect(Unit) { mapView ->
            mapView.mapboxMap.loadStyle("mapbox://styles/basebeta/cla1us0xn000314mob3xabfm2") { style ->
               val bluePinBitmap = getBitmapFromVectorDrawable(
                  context,
                  R.drawable.ic_exit_marker_medium
               )!!

               style.addImage("blue-pin", bluePinBitmap)
            }

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

@OptIn(MapboxExperimental::class)
@Composable
fun ViewAnnotationMarker(isViewAnnotationVisible: MutableState<Boolean>) {
   if (isViewAnnotationVisible.value) {
      ViewAnnotation(
         options = viewAnnotationOptions {
            geometry(Point.fromLngLat(-119.533632, 37.746011))
            allowOverlapWithPuck(true)
            annotationAnchor {
               anchor(ViewAnnotationAnchor.BOTTOM)
            }
         }
      ) {
         val bgColor = Color.DarkGray
         val bg = if (isSystemInDarkTheme()) bgColor else Color.White
         Box(Modifier.width(300.dp), contentAlignment = Alignment.Center) {
            Row(
               modifier = Modifier.height(35.dp)
                  .background(bg),
               horizontalArrangement = Arrangement.Center,
               verticalAlignment = Alignment.CenterVertically
            ) {
               Spacer(Modifier.width(2.dp))
               Text(
                  text = "Half Dome",
                  fontSize = 13.sp
               )
               Spacer(Modifier.width(2.dp))
            }
         }
      }
   }
}

fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap? {
   var drawable = ContextCompat.getDrawable(context, drawableId)
   val bitmap = Bitmap.createBitmap(
      drawable!!.intrinsicWidth,
      drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
   )
   val canvas = Canvas(bitmap)
   drawable.setBounds(0, 0, canvas.width, canvas.height)
   drawable.draw(canvas)
   return bitmap
}

data class MapboxCameraState(
   val center: LatLng,
   val zoom: Double = 9.0,
   val pitch: Double = 0.0,
   val bearing: Double = 0.0
)


data class LatLng(var latitude: Double = 0.toDouble(),
                  var longitude: Double = 0.toDouble()) {
   val isZero: Boolean
      get() = latitude == 0.0 && longitude == 0.0

   override fun toString(): String {
      return "LatLng{" +
            "latitude=" + latitude +
            ", longitude=" + longitude +
            '}'.toString()
   }
}
