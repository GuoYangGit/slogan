> 本文关于项目配置相关讲解，涉及通用 `gradle` 配置、代码混淆、App 打包等相关操作。

## config.gradle

> 业务功能模块和基础功能模块公用 `.gradle` 文件

### 通用插件

```groovy
// 判断是否可以单独编译
def isAppModule = project.getName().contains("app") || isModule.toBoolean()
if (isAppModule) {
    apply plugin: 'com.android.application'
} else {
    apply plugin: 'com.android.library'
}
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-parcelize'
apply plugin: 'kotlin-kapt'
apply plugin: 'com.google.dagger.hilt.android'
apply plugin: 'com.alibaba.arouter'
```

### Android 配置

```groovy
android {
    compileSdkVersion compile_sdk

    defaultConfig {
        minSdkVersion min_sdk
        targetSdkVersion compile_sdk
        versionCode version_code
        versionName version_name
        multiDexEnabled true
        ndk {
            // 选择要添加的对应 cpu 类型的 .so 库，多个abi以“,”分隔。
            abiFilters 'armeabi-v7a', 'arm64-v8a'
            // 可指定的值为 'armeabi-v7a', 'arm64-v8a', 'armeabi', 'x86', 'x86_64'，
        }
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        // 开启module混淆
        consumerProguardFiles "consumer-rules.pro"
    }

    buildFeatures {
        viewBinding true // 开启viewBinding
    }

    buildTypes {
        release {
            minifyEnabled true // 开启代码混淆
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
        debug {
            minifyEnabled false // 开启代码混淆
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = '1.8'
    }
    kapt {
        arguments {
            arg("AROUTER_MODULE_NAME", project.getName()) // 参数为每个 module 的名称
        }
        correctErrorTypes true // 开启kapt的错误修复
    }
    lintOptions {
        checkReleaseBuilds false //关闭lint检查
        abortOnError false // lint检查出错时，是否中断编译
    }
}
```

### 通用依赖

```groovy
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    //ARouter https://github.com/alibaba/ARouter/blob/master/README_CN.md
    implementation 'com.alibaba:arouter-api:1.5.2'
    kapt 'com.alibaba:arouter-compiler:1.5.2'
    //hilt https://developer.android.com/training/dependency-injection/hilt-android
    implementation "com.google.dagger:hilt-android:$hilt_version"
    kapt "com.google.dagger:hilt-compiler:$hilt_version"

    //androidx
    implementation 'androidx.appcompat:appcompat:1.5.1'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'com.google.android.material:material:1.7.0'
    implementation 'androidx.swiperefreshlayout:swiperefreshlayout:1.1.0'

    //kotlin
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation 'androidx.core:core-ktx:1.9.0'
    implementation "androidx.fragment:fragment-ktx:1.5.3"

    //lifecycle
    def lifecycleVersion = '2.4.1'
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:${lifecycleVersion}"
    implementation "androidx.lifecycle:lifecycle-common-java8:${lifecycleVersion}"
    implementation "androidx.lifecycle:lifecycle-process:${lifecycleVersion}"
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:${lifecycleVersion}"

    //test
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
    debugImplementation 'com.squareup.leakcanary:leakcanary-android:2.8.1'
}
```

## 单独编译

> 项目使用组件化形式，每个组件都可以单独编译，单独编译时，需要在 `gradle.properties` 文件中配置 `isModule=true`，这样就可以单独编译了。

### 业务模块 build.gradle 配置

```groovy
android {
    namespace 'com.huafang.module_home'
    resourcePrefix "home_" // 资源前缀,避免资源冲突
    defaultConfig {
        if (isModule.toBoolean()) { // 单独编译
            applicationId 'com.huafang.module_home'
        }
    }
    sourceSets {
        main {
            if (isModule.toBoolean()) { // 单独编译,设置清单文件路径
                manifest.srcFile 'src/main/debug/AndroidManifest.xml'
            } else {
                manifest.srcFile 'src/main/AndroidManifest.xml'
            }
        }
    }
}
```

## 代码混淆

> 默认情况下 `release` 模式下混淆功能开启，`debug` 模式下混淆功能关闭，如果新增功能需要进行混淆验证，可以在 `debug` 模式下开启混淆，具体设置在 `config.gradle` 文件。

### 配置混淆规则

如果在编码阶段需要配置混淆规则，无需在 `app` 模块下进行配置，只需在当前业务/基础功能模块的 `consumer-rules.pro` 文件中进行规则配置，打包 Apk 时会自动合并混淆规则。原因：在 `config.gradle` 文件中 `consumerProguardFiles "consumer-rules.pro"` 进行了合并操作。

**如果配置了混淆规则，一定要在 `debug` 模式下开启混淆功能进行验证。**

> **注意：`debug` 模式下开启代码混淆功能，需要注释 `Leakcanary` 内存泄露检测依赖，不然会导致 `App` 崩溃。**

## 业务模块资源冲突解决

如果多个模块开发，不同模块之间的开发人员并不知道其他业务模块开发人员的资源名定义，在**打包时重名资源会进行合并替换**，会导致业务上出现问题，所以我们需要在前期就进行解决，解决方案如下：

```groovy
// 业务模块 build.gradle 文件
android {
    resourcePrefix "xxx_" // xxx为功能模块名称
}
```

## App 打包

### 打包代码

关于 `Gradle` 打包相关代码逻辑在 `app` 模块的 `build.gradle` 文件下。

```groovy
android {
    signingConfigs {
        release {
            storeFile file(RELEASE_STORE_FILE)
            storePassword RELEASE_STORE_PASSWORD
            keyAlias RELEASE_KEY_ALIAS
            keyPassword RELEASE_KEY_PASSWORD
        }
        debug {
            storeFile file(RELEASE_STORE_FILE)
            storePassword RELEASE_STORE_PASSWORD
            keyAlias RELEASE_KEY_ALIAS
            keyPassword RELEASE_KEY_PASSWORD
        }
    }

    // 版本名字
    def appName = "Slogan_" + new Date().format('yyyy.MM.dd')
    // 修改apk文件名字
    android.applicationVariants.all { variant ->
        if (variant.buildType.name == 'release') {
            variant.outputs.all {
                def newName = "${appName}_${versionName}_${versionCode}.apk"
                outputFileName = newName
            }
        }
    }
}
```

### 签名信息

打包需要的的签名信息配置在 `gradle.properties` 文件下，**出于安全性考虑，该文件需要被 `git` 版本管理进行忽略**。

## 建议

### 尽量使用 `implementation` 进行依赖

使用 `implementation` 来进行依赖，这样会改善工程的构建时间，同时上层模块无法感知本模块的具体实现，减少代码耦合，方便后期的扩展和实现替换。
