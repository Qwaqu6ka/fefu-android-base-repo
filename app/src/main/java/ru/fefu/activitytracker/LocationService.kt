package ru.fefu.activitytracker

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import ru.fefu.currentActivity.MapActivity
import ru.fefu.database.App
import ru.fefu.database.Point
import ru.fefu.mainmenu.MainPartActivity
import java.util.*

class LocationService : Service() {

    companion object {
        var IS_ALIVE = false
        private const val TAG = "ForegroundService"
        private const val CHANNEL_ID = "foreground_service_id"
        private const val EXTRA_ID = "id"

        private const val ACTION_START = "start"
        const val ACTION_CANCEL = "cancel"

        val locationRequest: LocationRequest
            get() = LocationRequest.create()
                .setInterval(10000L)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setSmallestDisplacement(20f)

        fun startForeground(context: Context, id: Int) {
            val intent = Intent(context, LocationService::class.java)
            intent.putExtra(EXTRA_ID, id)
            intent.action = ACTION_START
            ContextCompat.startForegroundService(context, intent)
        }
    }

    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(this) }

    private var locationCallback: LocationCallback? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.d(TAG, "onStartCommand; ${intent?.getIntExtra(EXTRA_ID, -1)}")
        if (intent?.action == ACTION_CANCEL) {
            IS_ALIVE = false
            val id = App.INSTANCE.db.activeDao().getLastActiveId()
            App.INSTANCE.db.activeDao().finishActivity(id, Date())
//            val pendingIntent = PendingIntent.getActivity(
//                this,
//                0,
//                Intent(this, MainPartActivity::class.java),
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
//            )
//            startActivity(pendingIntent)
            stopLocationUpdates()
            stopForeground(true)
            stopSelf()
            return START_NOT_STICKY
        } else if (intent?.action == ACTION_START) {
            IS_ALIVE = true
            startLocationUpdates((intent.getIntExtra(EXTRA_ID, -1)).toLong())
            return START_REDELIVER_INTENT
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        locationCallback?.let { fusedLocationClient.removeLocationUpdates(it) }
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates(id: Long) {
        if (id == (-1).toLong()) stopSelf()
        //check if permission denied then stopSelf
        locationCallback?.let { fusedLocationClient.removeLocationUpdates(it) }
        ActivityLocationCallback(id).apply {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                this,
                Looper.getMainLooper()
            )
            locationCallback = this
        }
        showNotification()
    }

    private fun stopLocationUpdates() {
        locationCallback?.let { fusedLocationClient.removeLocationUpdates(it) }
    }

    private fun showNotification() {
        createChannelIfNeeded()
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MapActivity::class.java),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )
        val cancelIntent = Intent(this, LocationService::class.java).apply {
            action = ACTION_CANCEL
        }
        val cancelPendingIntent = PendingIntent.getService(
            this,
            1,
            cancelIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Hello")
            .setContentText("Tracking your activity")
            .setSmallIcon(R.drawable.map_point)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.map_point, "Stop", cancelPendingIntent)
            .build()
        startForeground(1, notification)
    }

    private fun createChannelIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Default channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    inner class ActivityLocationCallback(private val activityId: Long) : LocationCallback() {

        var list: MutableList<Point> = mutableListOf()

        override fun onLocationResult(result: LocationResult) {
            val lastLocation = result.lastLocation
            // put values into db
            list.add(Point(lastLocation.latitude, lastLocation.longitude))
            App.INSTANCE.db.activeDao().updateCoords(activityId, list)
            Log.d(TAG, ""+list)
            Log.d(TAG, "Latitude: ${lastLocation.latitude}; Longitude: ${lastLocation.longitude}")
        }
    }
}