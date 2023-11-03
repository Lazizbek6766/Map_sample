package uz.megasoft.mapgaotish.h

data class Step(
    val distance: Distance,
    val duration: DurationX,
    val end_location: EndLocationX,
    val html_instructions: String,
    val polyline: Polyline,
    val start_location: StartLocationX,
    val travel_mode: String
)