package uz.megasoft.mapgaotish.h

data class Model(
    val geocoded_waypoints: List<GeocodedWaypoint>,
    val routes: List<Route>,
    val status: String
)