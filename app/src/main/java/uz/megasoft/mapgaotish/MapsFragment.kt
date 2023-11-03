package uz.megasoft.mapgaotish

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.maps.android.PolyUtil
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.SquareCap
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.megasoft.mapgaotish.model_map.Directions
import uz.megasoft.mapgaotish.networking.RetrofitClient

class MapsFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_maps, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        callDirectionsApi()
        mMap.uiSettings.isZoomControlsEnabled = true
    }

    private fun callDirectionsApi(){

        // Origin of route
        val origin = "41.770168039580966,60.70637320394436"
        // Destination of route
        val dest = "41.773937866145864,60.68937252242257"

        val directionsCall = RetrofitClient.retrofitClient().getDirections(origin, dest,
            "Driving", "AIzaSyCX0AyplniB2J8KldlwqZkj-WQ1I49k3_I")
        directionsCall.enqueue(object : Callback<Directions> {
            override fun onResponse(call: Call<Directions>, response: Response<Directions>) {
                val directions = response.body()!!
                if (directions.status == "OK") {
                    Log.d("TAG", "onResponse: ${directions.routes[0].overviewPolyline.points.count()}")
                    val time = directions.routes[0].legs[0].duration.text
                    val distance = directions.routes[0].legs[0].distance.text
                    Toast.makeText(requireContext(), "Time: $time Distance: $distance", Toast.LENGTH_LONG).show()
                    drawPolyline(directions.routes[0].overviewPolyline.points)
                } else {
                    Toast.makeText(requireContext(), directions.status, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Directions>, t: Throwable) {

                Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun drawPolyline(points: String){

        val polylineOptions = PolylineOptions()
        polylineOptions.geodesic(true)
        polylineOptions.color(ContextCompat.getColor(requireContext(), R.color.black))
        polylineOptions.startCap(SquareCap())
        polylineOptions.endCap(SquareCap())

        val pointsList = PolyUtil.decode(points)
        for (point in pointsList) {
            polylineOptions.add(point)
        }

        mMap.addPolyline(polylineOptions)
    }

}