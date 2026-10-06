#
# SPDX-FileCopyrightText: The LineageOS Project
# SPDX-License-Identifier: Apache-2.0
#

## Inherit from the common tree
include device/samsung/m12-common/BoardConfigCommon.mk

## Inherit from the proprietary configuration
include vendor/samsung/m12/BoardConfigVendor.mk

DEVICE_PATH := device/samsung/m12

## APEX image
DEXPREOPT_GENERATE_APEX_IMAGE := true

## Display
TARGET_SCREEN_DENSITY := 300

## Kernel
TARGET_KERNEL_CONFIG := m12_defconfig

## OTA
TARGET_OTA_ASSERT_DEVICE := m12

## Properties
TARGET_VENDOR_PROP += $(DEVICE_PATH)/vendor.prop
