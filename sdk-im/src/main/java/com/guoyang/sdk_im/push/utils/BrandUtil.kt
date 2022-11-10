package com.guoyang.sdk_im.push.utils

import android.os.Build
import com.guoyang.sdk_im.push.PrivateConstants

object BrandUtil {
    var brandID = 0L
        get() {
            if (field == 0L) {
                field = when {
                    isBrandXiaoMi -> {
                        PrivateConstants.BRAND_XIAOMI
                    }
                    isBrandHonor -> {
                        PrivateConstants.BRAND_HONOR
                    }
                    isBrandHuawei -> {
                        PrivateConstants.BRAND_HUAWEI
                    }
                    isBrandMeizu -> {
                        PrivateConstants.BRAND_MEIZU
                    }
                    isBrandOppo -> {
                        PrivateConstants.BRAND_OPPO
                    }
                    isBrandVivo -> {
                        PrivateConstants.BRAND_VIVO
                    }
                    else -> {
                        PrivateConstants.BRAND_GOOLE_ELSE
                    }
                }
            }
            return field
        }

    private val buildBrand: String
        get() = Build.BRAND

    private val buildManufacturer: String
        get() = Build.MANUFACTURER

    private val isBrandXiaoMi: Boolean
        get() = ("xiaomi".equals(
            buildBrand,
            ignoreCase = true
        ) || "xiaomi".equals(buildManufacturer, ignoreCase = true))
    private val isBrandHuawei: Boolean
        get() = "huawei".equals(buildBrand, ignoreCase = true) || "huawei".equals(
            buildManufacturer,
            ignoreCase = true
        ) || "honor".equals(buildBrand, ignoreCase = true)

    private val isBrandMeizu: Boolean
        get() = ("meizu".equals(buildBrand, ignoreCase = true) || "meizu".equals(
            buildManufacturer,
            ignoreCase = true
        ) || "22c4185e".equals(buildBrand, ignoreCase = true))

    //                || MzSystemUtils.isBrandMeizu(PrivateConstants.getInstance().getContext());
    private val isBrandOppo: Boolean
        get() = "oppo".equals(buildBrand, ignoreCase = true) || "realme".equals(
            buildBrand,
            ignoreCase = true
        ) || "oneplus".equals(buildBrand, ignoreCase = true) || "oppo".equals(
            buildManufacturer,
            ignoreCase = true
        ) || "realme".equals(buildManufacturer, ignoreCase = true) || "oneplus".equals(
            buildManufacturer,
            ignoreCase = true
        )

    private val isBrandVivo: Boolean
        get() = ("vivo".equals(buildBrand, ignoreCase = true) || "vivo".equals(
            buildManufacturer,
            ignoreCase = true
        ))

    private val isBrandHonor: Boolean
        get() = "honor".equals(buildBrand, ignoreCase = true) && "honor".equals(
            buildManufacturer, ignoreCase = true
        )
}