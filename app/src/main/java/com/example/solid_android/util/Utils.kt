package com.example.solid_android.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.*
import android.util.Base64
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


fun String.isEmailValid(): Boolean {
    val regex = Patterns.EMAIL_ADDRESS
    return regex.matcher(this).matches()
}


fun dpToPx(context: Context, dp: Int): Int {
    return (dp * (context.resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}

fun pxToDp(context: Context, px: Int): Int {
    return (px / (context.resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}

fun Float.toDp(context: Context): Float {
    return (this / (context.resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
}

fun Float.toPx(context: Context): Float {
    return (this * (context.resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
}

fun spToPx(context: Context, sp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        sp.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}

inline fun <reified Retro, reified Room> convertRetroToRoom(item: Retro): Room {
    val json = Gson().toJson(item)
    return Gson().fromJson(json, Room::class.java)
}

fun isNetworkConnected(context: Context): Boolean {
    val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    connMgr ?: return false
    val network: Network = connMgr.activeNetwork ?: return false
    val capabilities = connMgr.getNetworkCapabilities(network)
    return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
            || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
}

fun convertBase64ToBitmap(base64String: String): Bitmap? {
    var bitmap: Bitmap? = null
    try {
        val b64 = base64String.replace("data:image/png;base64,", "")
        val imageAsBytes: ByteArray = Base64.decode(b64.toByteArray(), Base64.DEFAULT)
        bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size)
    } catch (ignored: Exception) {
    }
    return bitmap
}

fun getScreenWidth(activity: Activity): Int {
    val displayMetrics = DisplayMetrics()
    activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.widthPixels
}

fun getScreenHeight(activity: Activity): Int {
    val displayMetrics = DisplayMetrics()
    activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.heightPixels
}

fun <T : ViewModel> Fragment.obtainViewModel(
    owner: ViewModelStoreOwner,
    viewModelClass: Class<T>,
    viewmodelFactory: ViewModelProvider.Factory,
) = ViewModelProvider(owner, viewmodelFactory).get(viewModelClass)

fun String.convertStringToDate(): Date? {
    try {
        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
        return sdf.parse(this)
    } catch (e: Exception) {
        return null
    }
}

fun String.convertLongToDateString(): String? {
    try {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val timestamp = this.toLong() * 1000
        return sdf.format(Date(timestamp))
    } catch (e: Exception) {
        return null
    }
}

fun String.convertLongToDateString2(): String? {
    try {
        val sdf = SimpleDateFormat("MMMM, dd yyyy", Locale.getDefault())
        val timestamp = this.toLong() * 1000
        return sdf.format(Date(timestamp))
    } catch (e: Exception) {
        return null
    }
}

fun Long.convertLongToDate(): String? {
    try {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return sdf.format(Date(this))
    } catch (e: Exception) {
        return null
    }
}

fun String.convertLongToSummaryDate(): String? {
    try {
        val sdf = SimpleDateFormat("EEEE, dd MMM yyyy, hh:mm a", Locale.getDefault())
        val timestamp = this.toLong() * 1000
        return sdf.format(Date(timestamp))
    } catch (e: Exception) {
        return null
    }
}

fun getFutureDate(): Date {
    val cal = Calendar.getInstance()
    cal.add(Calendar.YEAR, 1000)
    return cal.time
}

fun getDisplaySize(context: Context): String {
    val density = context.resources.displayMetrics.density
    if (density == 0.75f) {
        return "small"
    } else if (density >= 1.0f && density < 1.5f) {
        return "small"
    } else if (density == 1.5f) {
        return "small"
    } else if (density > 1.5f && density <= 2.0f) {
        return "medium"
    } else if (density > 2.0f && density <= 3.0f) {
        return "large"
    } else {
        return "extraLarge"
    }
}

fun TextView.leftDrawable(id: Drawable) {
    this.setCompoundDrawablesWithIntrinsicBounds(id, null, null, null)
}

fun <T, K, R> LiveData<T>.combineWith(
    liveData: LiveData<K>,
    block: (T?, K?) -> R,
): LiveData<R> {
    val result = MediatorLiveData<R>()
    result.addSource(this) {
        result.value = block(this.value, liveData.value)
    }
    result.addSource(liveData) {
        result.value = block(this.value, liveData.value)
    }
    return result
}

fun Snackbar.setOnClickListener(onClickListener: View.OnClickListener) {
    val sbView: View = this.getView()
    sbView.isClickable = true
    sbView.isFocusable = true

    sbView.setOnClickListener(onClickListener)
}

fun Snackbar.textAlign(gravity: Int) {
    val sbView: View = this.getView()
    val tv = sbView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    tv.setTextColor(0xFF8ADA2B.toInt())
    //tv.gravity = gravity
    tv.textAlignment = gravity

}

fun Snackbar.centerAlignSnackBar() {
    val sbView: View = this.getView()
    val params: FrameLayout.LayoutParams = sbView.layoutParams as FrameLayout.LayoutParams
    params.width = FrameLayout.LayoutParams.WRAP_CONTENT
    params.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
    sbView.layoutParams = params
}

enum class DisplayDensityQualifier {
    LDPI,
    MDPI,
    HDPI,
    XHDPI,
    XXHDPI,
    XXXHDPI
}

private const val displayDensity = "DisplayDensity"

fun getDisplayDensityQualifier(context: Context): DisplayDensityQualifier {
    val density: Float = context.resources.displayMetrics.density

    return if (density == 0.75f) {
        // LDPI
        Log.i(displayDensity, "LDPI")
        DisplayDensityQualifier.LDPI
    } else if (density >= 1.0f && density < 1.5f) {
        // MDPI
        Log.i(displayDensity, "MDPI")
        DisplayDensityQualifier.MDPI
    } else if (density == 1.5f) {
        // HDPI
        Log.i(displayDensity, "HDPI")
        DisplayDensityQualifier.HDPI
    } else if (density > 1.5f && density <= 2.0f) {
        // XHDPI
        Log.i(displayDensity, "XHDPI")
        DisplayDensityQualifier.XHDPI
    } else if (density > 2.0f && density <= 3.0f) {
        // XXHDPI
        Log.i(displayDensity, "XXHDPI")
        DisplayDensityQualifier.XXHDPI
    } else {
        // XXXHDPI
        Log.i(displayDensity, "XXXHDPI")
        DisplayDensityQualifier.XXXHDPI
    }
}

fun printDisplayDensity(context: Context) {
    Log.i(displayDensity, getDisplayDensityQualifier(context).name)
}

fun to2DecimalPlaces(value: Float): String {
    return String.format("%.2f", value)
}


fun Drawable.isLandScape(): Boolean {
    return this.intrinsicWidth >= this.intrinsicHeight
}

fun Drawable.isPortrait(): Boolean {
    return this.intrinsicWidth < this.intrinsicHeight
}

fun Drawable.scaleToXPercentOfScreen(context: Context, percentage: Float) {
    val scale = percentage / 100f
    val displayMetrics = context.resources.displayMetrics
    val isLandscape = this.isLandScape()

    var imageAspectRatio: Float = 0f;

    if (isLandscape) {
        imageAspectRatio = (this.intrinsicWidth.toFloat() / this.intrinsicHeight.toFloat())

        val scaledWidth = (displayMetrics.widthPixels * scale).roundToInt()
        val scaledHeight = scaledWidth / imageAspectRatio
        this.setBounds(0, 0, scaledWidth, scaledHeight.toInt())
    } else {
        imageAspectRatio = (this.intrinsicHeight.toFloat() / this.intrinsicWidth.toFloat())
        val scaledHeight = (displayMetrics.heightPixels * scale).roundToInt()
        val scaledWidth = scaledHeight / imageAspectRatio
        this.setBounds(0, 0, scaledWidth.toInt(), scaledHeight)
    }
}

fun RecyclerView.addScrollListener(onScroll: (position: Int) -> Unit) {
    var lastPosition = 0
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (layoutManager is LinearLayoutManager) {
                val currentVisibleItemPosition =
                    (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if (lastPosition != currentVisibleItemPosition && currentVisibleItemPosition != RecyclerView.NO_POSITION) {
                    onScroll.invoke(currentVisibleItemPosition)
                    lastPosition = currentVisibleItemPosition
                }
            }
        }
    })
}