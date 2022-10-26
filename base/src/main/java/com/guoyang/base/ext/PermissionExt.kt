package com.guoyang.base.ext

import android.Manifest
import android.os.Build
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.permissionx.guolindev.PermissionX
import com.permissionx.guolindev.request.ExplainScope
import com.permissionx.guolindev.request.ForwardScope

/**
 * @author yang.guo on 2022/10/13
 * @describe App权限扩展类
 * Manifest.permission.CAMERA 相机权限
 */

/**
 * FragmentActivity获取读写权限扩展方法
 * @param explainReasonBeforeRequest: 请求权限之前告知用户为何要用
 * @param onExplainRequestReason:
 * @param onForwardToSettings:
 * @param request: 权限回调
 */
fun FragmentActivity.requestReadOrWritePermissions(
    explainReasonBeforeRequest: Boolean = false,
    onExplainRequestReason: (scope: ExplainScope, deniedList: List<String>) -> Unit = { _, _ -> },
    onForwardToSettings: (scope: ForwardScope, deniedList: List<String>) -> Unit = { _, _ -> },
    request: (allGranted: Boolean, grantedList: List<String>, deniedList: List<String>) -> Unit
) {
    val permissionList = arrayListOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        permissionList.add(Manifest.permission.READ_MEDIA_IMAGES)
        permissionList.add(Manifest.permission.READ_MEDIA_AUDIO)
        permissionList.add(Manifest.permission.READ_MEDIA_VIDEO)
    } else {
        permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
    requestPermissions(
        permissionList,
        explainReasonBeforeRequest,
        onExplainRequestReason,
        onForwardToSettings,
        request
    )
}

/**
 * FragmentActivity获取读写权限扩展方法
 * @param explainReasonBeforeRequest: 请求权限之前告知用户为何要用
 * @param onExplainRequestReason:
 * @param onForwardToSettings:
 * @param request: 权限回调
 */
fun Fragment.requestReadOrWritePermissions(
    explainReasonBeforeRequest: Boolean = false,
    onExplainRequestReason: (scope: ExplainScope, deniedList: List<String>) -> Unit = { _, _ -> },
    onForwardToSettings: (scope: ForwardScope, deniedList: List<String>) -> Unit = { _, _ -> },
    request: (allGranted: Boolean, grantedList: List<String>, deniedList: List<String>) -> Unit
) {
    val permissionList = arrayListOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        permissionList.add(Manifest.permission.READ_MEDIA_IMAGES)
        permissionList.add(Manifest.permission.READ_MEDIA_AUDIO)
        permissionList.add(Manifest.permission.READ_MEDIA_VIDEO)
    } else {
        permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
    requestPermissions(
        permissionList,
        explainReasonBeforeRequest,
        onExplainRequestReason,
        onForwardToSettings,
        request
    )
}

/**
 * FragmentActivity获取相机、录音权限扩展方法
 * @param explainReasonBeforeRequest: 请求权限之前告知用户为何要用
 * @param onExplainRequestReason:
 * @param onForwardToSettings:
 * @param request: 权限回调
 */
fun FragmentActivity.requestCameraPermissions(
    explainReasonBeforeRequest: Boolean = false,
    onExplainRequestReason: (scope: ExplainScope, deniedList: List<String>) -> Unit = { _, _ -> },
    onForwardToSettings: (scope: ForwardScope, deniedList: List<String>) -> Unit = { _, _ -> },
    request: (allGranted: Boolean, grantedList: List<String>, deniedList: List<String>) -> Unit
) {
    val permissionList = arrayListOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
    requestPermissions(
        permissionList,
        explainReasonBeforeRequest,
        onExplainRequestReason,
        onForwardToSettings,
        request
    )
}

/**
 * FragmentActivity获取相机、录音权限扩展方法
 * @param explainReasonBeforeRequest: 请求权限之前告知用户为何要用
 * @param onExplainRequestReason:
 * @param onForwardToSettings:
 * @param request: 权限回调
 */
fun Fragment.requestCameraPermissions(
    explainReasonBeforeRequest: Boolean = false,
    onExplainRequestReason: (scope: ExplainScope, deniedList: List<String>) -> Unit = { _, _ -> },
    onForwardToSettings: (scope: ForwardScope, deniedList: List<String>) -> Unit = { _, _ -> },
    request: (allGranted: Boolean, grantedList: List<String>, deniedList: List<String>) -> Unit
) {
    val permissionList = arrayListOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
    requestPermissions(
        permissionList,
        explainReasonBeforeRequest,
        onExplainRequestReason,
        onForwardToSettings,
        request
    )
}

/**
 * FragmentActivity获取定位权限扩展方法
 * @param explainReasonBeforeRequest: 请求权限之前告知用户为何要用
 * @param onExplainRequestReason:
 * @param onForwardToSettings:
 * @param request: 权限回调
 */
fun FragmentActivity.requestLocationPermissions(
    explainReasonBeforeRequest: Boolean = false,
    onExplainRequestReason: (scope: ExplainScope, deniedList: List<String>) -> Unit = { _, _ -> },
    onForwardToSettings: (scope: ForwardScope, deniedList: List<String>) -> Unit = { _, _ -> },
    request: (allGranted: Boolean, grantedList: List<String>, deniedList: List<String>) -> Unit
) {
    val permissionList = arrayListOf(Manifest.permission.ACCESS_FINE_LOCATION)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        permissionList.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
    }
    requestPermissions(
        permissionList,
        explainReasonBeforeRequest,
        onExplainRequestReason,
        onForwardToSettings,
        request
    )
}

/**
 * FragmentActivity获取定位权限扩展方法
 * @param explainReasonBeforeRequest: 请求权限之前告知用户为何要用
 * @param onExplainRequestReason:
 * @param onForwardToSettings:
 * @param request: 权限回调
 */
fun Fragment.requestLocationPermissions(
    explainReasonBeforeRequest: Boolean = false,
    onExplainRequestReason: (scope: ExplainScope, deniedList: List<String>) -> Unit = { _, _ -> },
    onForwardToSettings: (scope: ForwardScope, deniedList: List<String>) -> Unit = { _, _ -> },
    request: (allGranted: Boolean, grantedList: List<String>, deniedList: List<String>) -> Unit
) {
    val permissionList = arrayListOf(Manifest.permission.ACCESS_FINE_LOCATION)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        permissionList.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
    }
    requestPermissions(
        permissionList,
        explainReasonBeforeRequest,
        onExplainRequestReason,
        onForwardToSettings,
        request
    )
}

/**
 * FragmentActivity获取悬浮窗权限扩展方法
 * @param explainReasonBeforeRequest: 请求权限之前告知用户为何要用
 * @param onExplainRequestReason:
 * @param onForwardToSettings:
 * @param request: 权限回调
 */
fun FragmentActivity.requestNotificationPermissions(
    explainReasonBeforeRequest: Boolean = false,
    onExplainRequestReason: (scope: ExplainScope, deniedList: List<String>) -> Unit = { _, _ -> },
    onForwardToSettings: (scope: ForwardScope, deniedList: List<String>) -> Unit = { _, _ -> },
    request: (allGranted: Boolean, grantedList: List<String>, deniedList: List<String>) -> Unit
) {
    val permissionList = arrayListOf(PermissionX.permission.POST_NOTIFICATIONS)
    requestPermissions(
        permissionList,
        explainReasonBeforeRequest,
        onExplainRequestReason,
        onForwardToSettings,
        request
    )
}

/**
 * FragmentActivity获取悬浮窗权限扩展方法
 * @param explainReasonBeforeRequest: 请求权限之前告知用户为何要用
 * @param onExplainRequestReason:
 * @param onForwardToSettings:
 * @param request: 权限回调
 */
fun Fragment.requestNotificationPermissions(
    explainReasonBeforeRequest: Boolean = false,
    onExplainRequestReason: (scope: ExplainScope, deniedList: List<String>) -> Unit = { _, _ -> },
    onForwardToSettings: (scope: ForwardScope, deniedList: List<String>) -> Unit = { _, _ -> },
    request: (allGranted: Boolean, grantedList: List<String>, deniedList: List<String>) -> Unit
) {
    val permissionList = arrayListOf(PermissionX.permission.POST_NOTIFICATIONS)
    requestPermissions(
        permissionList,
        explainReasonBeforeRequest,
        onExplainRequestReason,
        onForwardToSettings,
        request
    )
}

/**
 * FragmentActivity获取悬浮窗权限扩展方法
 * @param explainReasonBeforeRequest: 请求权限之前告知用户为何要用
 * @param onExplainRequestReason:
 * @param onForwardToSettings:
 * @param request: 权限回调
 */
fun FragmentActivity.requestSystemAlertWindowPermissions(
    explainReasonBeforeRequest: Boolean = false,
    onExplainRequestReason: (scope: ExplainScope, deniedList: List<String>) -> Unit = { _, _ -> },
    onForwardToSettings: (scope: ForwardScope, deniedList: List<String>) -> Unit = { _, _ -> },
    request: (allGranted: Boolean, grantedList: List<String>, deniedList: List<String>) -> Unit
) {
    val permissionList = arrayListOf(Manifest.permission.SYSTEM_ALERT_WINDOW)
    requestPermissions(
        permissionList,
        explainReasonBeforeRequest,
        onExplainRequestReason,
        onForwardToSettings,
        request
    )
}

/**
 * FragmentActivity获取悬浮窗权限扩展方法
 * @param explainReasonBeforeRequest: 请求权限之前告知用户为何要用
 * @param onExplainRequestReason:
 * @param onForwardToSettings:
 * @param request: 权限回调
 */
fun Fragment.requestSystemAlertWindowPermissions(
    explainReasonBeforeRequest: Boolean = false,
    onExplainRequestReason: (scope: ExplainScope, deniedList: List<String>) -> Unit = { _, _ -> },
    onForwardToSettings: (scope: ForwardScope, deniedList: List<String>) -> Unit = { _, _ -> },
    request: (allGranted: Boolean, grantedList: List<String>, deniedList: List<String>) -> Unit
) {
    val permissionList = arrayListOf(Manifest.permission.SYSTEM_ALERT_WINDOW)
    requestPermissions(
        permissionList,
        explainReasonBeforeRequest,
        onExplainRequestReason,
        onForwardToSettings,
        request
    )
}

/**
 * FragmentActivity获取权限适配扩展方法
 * @param permissions: 需要申请的权限列表
 * @param explainReasonBeforeRequest: 请求权限之前告知用户为何要用
 * @param onExplainRequestReason:
 * @param onForwardToSettings:
 * @param request: 权限回调
 */
private fun FragmentActivity.requestPermissions(
    permissions: List<String>,
    explainReasonBeforeRequest: Boolean = false,
    onExplainRequestReason: (scope: ExplainScope, deniedList: List<String>) -> Unit = { _, _ -> },
    onForwardToSettings: (scope: ForwardScope, deniedList: List<String>) -> Unit = { _, _ -> },
    request: (allGranted: Boolean, grantedList: List<String>, deniedList: List<String>) -> Unit
) {
    PermissionX
        .init(this)
        .permissions(permissions)
        .apply {
            //在实际请求这些权限之前，显示基本原理对话框并向用户解释为什么需要这些权限始终是一种好方法。
            //使用 PermissionX 做到这一点非常简单。只需使用explainReasonBeforeRequest方法
            if (explainReasonBeforeRequest) {
                explainReasonBeforeRequest()
            }
        }
        .onExplainRequestReason { scope, deniedList ->
            // Android 提供了 shouldShowRequestPermissionRationale方法来指示我们是否应该显示一个基本原理对话框来向用户解释我们为什么需要此权限。
            // 否则用户可能会拒绝我们请求的权限并检查过不再询问选项。
//            scope.showRequestReasonDialog(deniedList, "Core fundamental are based on these permissions", "OK", "Cancel")
            onExplainRequestReason(scope, deniedList)
        }
        .onForwardToSettings { scope, deniedList ->
            // PermissionX 提供了 onForwardToSettings方法来处理这种情况。将此方法链接在请求方法之前，如果某些权限被用户“拒绝并且不再询问”
//            scope.showForwardToSettingsDialog(deniedList, "You need to allow necessary permissions in Settings manually", "OK", "Cancel")
            onForwardToSettings(scope, deniedList)
        }
        .request { allGranted, grantedList, deniedList ->
            // allGranted表示您请求的所有权限是否由用户授予，可能是 true 或 false。
            // grantList持有所有授予的权限，
            // deniedList持有所有拒绝的权限。
            request(allGranted, grantedList, deniedList)
        }
}

/**
 * Fragment获取权限适配扩展方法
 * @param permissions: 需要申请的权限列表
 * @param explainReasonBeforeRequest: 请求权限之前告知用户为何要用
 * @param onExplainRequestReason:
 * @param onForwardToSettings:
 * @param request: 权限回调
 */
private fun Fragment.requestPermissions(
    permissions: List<String>,
    explainReasonBeforeRequest: Boolean = false,
    onExplainRequestReason: (scope: ExplainScope, deniedList: List<String>) -> Unit = { _, _ -> },
    onForwardToSettings: (scope: ForwardScope, deniedList: List<String>) -> Unit = { _, _ -> },
    request: (allGranted: Boolean, grantedList: List<String>, deniedList: List<String>) -> Unit
) {
    PermissionX
        .init(this)
        .permissions(permissions)
        .apply {
            //在实际请求这些权限之前，显示基本原理对话框并向用户解释为什么需要这些权限始终是一种好方法。
            //使用 PermissionX 做到这一点非常简单。只需使用explainReasonBeforeRequest方法
            if (explainReasonBeforeRequest) {
                explainReasonBeforeRequest()
            }
        }
        .onExplainRequestReason { scope, deniedList ->
            // Android 提供了 shouldShowRequestPermissionRationale方法来指示我们是否应该显示一个基本原理对话框来向用户解释我们为什么需要此权限。
            // 否则用户可能会拒绝我们请求的权限并检查过不再询问选项。
//            scope.showRequestReasonDialog(deniedList, "Core fundamental are based on these permissions", "OK", "Cancel")
            onExplainRequestReason(scope, deniedList)
        }
        .onForwardToSettings { scope, deniedList ->
            // PermissionX 提供了 onForwardToSettings方法来处理这种情况。将此方法链接在请求方法之前，如果某些权限被用户“拒绝并且不再询问”
//            scope.showForwardToSettingsDialog(deniedList, "You need to allow necessary permissions in Settings manually", "OK", "Cancel")
            onForwardToSettings(scope, deniedList)
        }
        .request { allGranted, grantedList, deniedList ->
            // allGranted表示您请求的所有权限是否由用户授予，可能是 true 或 false。
            // grantList持有所有授予的权限，
            // deniedList持有所有拒绝的权限。
            request(allGranted, grantedList, deniedList)
        }
}