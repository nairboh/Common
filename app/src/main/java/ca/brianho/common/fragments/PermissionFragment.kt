package ca.brianho.common.fragments

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

abstract class PermissionFragment : Fragment() {

    private companion object {
        private const val REQUEST_CODE = 100
    }

    protected fun promptForPermission(permission: String) {
        val permissionCheckResult = ContextCompat.checkSelfPermission(requireContext(), permission)

        // Permission already granted
        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
            onRequestPermissionSuccess(permission)
            return
        }

        // Permission not granted yet
        if (!shouldShowRequestPermissionRationale(permission)) {
            requestPermissions(arrayOf(permission), REQUEST_CODE)
            return
        }

        // user denied permission
    }

    final override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isEmpty()) {
                permissions.forEach(::onRequestPermissionFailed)
                return
            }

            permissions.forEachIndexed { index, permission ->
                if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                    onRequestPermissionSuccess(permission)
                } else {
                    onRequestPermissionFailed(permission)
                }
            }
        }

        // TODO: Handle permanently denied
    }

    abstract fun onRequestPermissionSuccess(permission: String)

    abstract fun onRequestPermissionFailed(permission: String)
}