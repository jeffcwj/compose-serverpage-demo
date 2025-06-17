package compose.serverpage.demo.utils

import android.content.Context

fun Context.Toast(msg: String, isLong: Boolean = false) {
    android.widget.Toast.makeText(this, msg, if (isLong) android.widget.Toast.LENGTH_LONG else android.widget.Toast.LENGTH_SHORT).show()
}