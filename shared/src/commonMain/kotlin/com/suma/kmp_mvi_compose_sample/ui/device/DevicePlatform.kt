//package com.suma.kmp_mvi_compose_sample.ui.device
//
///**
// * 平台信息
// */
//data class DeviceInfo(
//    val platformName: String,   // Android / iOS
//    val osVersion: String,      // 系统版本
//    val deviceModel: String,    // 机型
//    val cpuAbi: String,         // CPU 架构
//    val screenWidthPx: Int,
//    val screenHeightPx: Int,
//    val density: Float,
//)
//
//interface DeviceInfo {
//    fun platformName(): String
//    fun osVersion(): String
//    fun deviceModel(): String
//    fun cpuAbi(): String
//    fun screenWidthPx(): Int
//    fun screenHeightPx(): Int
//    fun density(): Float
//}
//
///** expect：编译期强制每个目标平台都提供 actual，否则编译失败 */
//expect fun currentDeviceInfo(): DeviceInfo