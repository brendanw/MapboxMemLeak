package com.mapbox.memleak

import android.content.Context
import android.widget.FrameLayout
import com.mapbox.maps.MapView

class MyMapView : FrameLayout {
   constructor(context: Context) : super(context) {
      val mapView = MapView(context)
      addView(mapView)
   }

}