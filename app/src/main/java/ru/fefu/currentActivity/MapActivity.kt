package ru.fefu.currentActivity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import org.osmdroid.config.Configuration
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import ru.fefu.activitytracker.LocationService
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.ActivityMapBinding
import ru.fefu.database.App
import java.lang.Exception

class MapActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CODE_RESOLVE_GOOGLE_API_ERROR = 1337
        private const val REQUEST_CODE_RESOLVE_GPS_ERROR = 1338
    }

    private lateinit var binding: ActivityMapBinding

    val polyline by lazy {
        Polyline().apply {
            outlinePaint.color = ContextCompat.getColor(
                this@MapActivity,
                R.color.purple_700
            )
        }
    }

    val permissionRequestLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions[Manifest.permission.ACCESS_FINE_LOCATION]?.let {
                if (it) {
                    showUserLocation()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
                    ) {
                        //Разрашение уже запрашивали, не выдали, и уже объясняли юзеру зачем нужно это разрешение
                        showPermissionDeniedDialog()
                    } else {
                        showRationaleDialog()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cardView: CardView = binding.cardView
        val fm: FragmentManager = supportFragmentManager

        cardView.setBackgroundResource(R.drawable.card_view_bg)

        val prepareFragment = fm.findFragmentByTag("PrepareFragment")
        val runFragment = fm.findFragmentByTag("RunFragment")

        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        if (LocationService.IS_ALIVE) {
            when (runFragment) {
                null -> fm.beginTransaction()
                            .add(R.id.container, RunFragment(), "RunFragment")
                            .commit()
                else -> fm.beginTransaction()
                            .show(runFragment)
                            .commit()
            }
        }
        else {
            when (prepareFragment) {
                null -> fm.beginTransaction()
                            .add(R.id.container, PrepareFragment(), "PrepareFragment")
                            .commit()
                else -> fm.beginTransaction()
                            .show(prepareFragment)
                            .commit()
            }
        }

        Configuration.getInstance().load(this, getPreferences(Context.MODE_PRIVATE))

        permissionRequestLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
        initMap()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        binding.mapView.onPause()
        super.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_RESOLVE_GOOGLE_API_ERROR ||
            requestCode == REQUEST_CODE_RESOLVE_GPS_ERROR) {
            if (resultCode == Activity.RESULT_OK) startLocationService()
        }
    }

    fun startLocationService() {
        if (isGooglePlayServicesAvailable()) {
            val id = App.INSTANCE.db.activeDao().getLastActiveId()
            checkIfGpsEnabled(
                { LocationService.startForeground(this, id) },
                {
                    if (it is ResolvableApiException) {
                        it.startResolutionForResult(this,
                            REQUEST_CODE_RESOLVE_GPS_ERROR
                        )
                    } else {
                        Toast.makeText(this, "GPS Unavailable", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }

    private fun isGooglePlayServicesAvailable(): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val result = googleApiAvailability.isGooglePlayServicesAvailable(this)
        if (result == ConnectionResult.SUCCESS) return true
        if (googleApiAvailability.isUserResolvableError(result)) {
            googleApiAvailability.getErrorDialog(
                this,
                result,
                REQUEST_CODE_RESOLVE_GOOGLE_API_ERROR
            )?.show()
        } else {
            Toast.makeText(
                this,
                "Google services unavailable",
                Toast.LENGTH_SHORT
            ).show()
        }
        return false
    }

    private fun checkIfGpsEnabled(success: () -> Unit, error: (Exception) -> Unit) {
        LocationServices.getSettingsClient(this)
            .checkLocationSettings(
                LocationSettingsRequest.Builder()
                    .addLocationRequest(LocationService.locationRequest)
                    .build()
            )
            .addOnSuccessListener { success.invoke() }
            .addOnFailureListener { error.invoke(it) }
    }

    private fun initMap() {
        binding.mapView.minZoomLevel = 4.0
        // post положит выполнение кода внутри в очередь,
        // что позволит выполнить этот код после полной инициализации mapView
        binding.mapView.post {
            binding.mapView.zoomToBoundingBox(
                BoundingBox(
                    43.232111,
                    132.117062,
                    42.968866,
                    131.768039
                ),
                false
            )
        }
        binding.mapView.overlayManager.add(polyline)
    }

    private fun showUserLocation() {
        val locationOverlay = MyLocationNewOverlay(
            object : GpsMyLocationProvider(applicationContext) {
                private var mapMoved = false
                override fun onLocationChanged(location: Location) {
                    // Location class doesn't has constructor for bearing remove
                    // With bearing mapView ignores hotspot & draws center of icon in center
                    // of user location, but we need to draw bottom of icon on user location
                    location.removeBearing()
                    super.onLocationChanged(location)
                    if (mapMoved) return
                    mapMoved = true
                    binding.mapView.controller.animateTo(
                        GeoPoint(
                            location.latitude,
                            location.longitude
                        ),
                        16.0,
                        1000
                    )
                }
            },
            binding.mapView
        )
        val locationIcon = BitmapFactory.decodeResource(resources, R.drawable.map_point)
        locationOverlay.setDirectionArrow(locationIcon, locationIcon)
        locationOverlay.setPersonHotspot(locationIcon.width / 2f, locationIcon.height.toFloat())
        locationOverlay.enableMyLocation()
        binding.mapView.overlays.add(locationOverlay)
    }

    private fun showRationaleDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission required")
            .setMessage("Вы не сможете узнать пройденный путь без этого разрешеня")
            .setPositiveButton("Разрешить") { _, _ ->
                permissionRequestLauncher.launch(arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ))
            }
            .setNegativeButton("Отмена") { _, _ -> }
            .show()
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Необходимо разрешение")
            .setMessage("Вы можете установить разрешение в настройках")
            .setPositiveButton("Настройки") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Отмена") { _, _ -> }
            .show()
    }
}