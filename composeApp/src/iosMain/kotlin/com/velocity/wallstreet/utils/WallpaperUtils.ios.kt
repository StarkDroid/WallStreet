package com.velocity.wallstreet.utils

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.request.get
import io.ktor.client.statement.readRawBytes
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.dataWithBytes
import platform.Photos.PHAssetChangeRequest
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHAuthorizationStatusLimited
import platform.Photos.PHAuthorizationStatusNotDetermined
import platform.Photos.PHPhotoLibrary
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import kotlin.coroutines.resume

class WallpaperUtilsIOS : WallpaperFunctions {

    override suspend fun setWallpaper(
        imageUrl: String,
        type: WallpaperType
    ): Result<Unit> {
        val downloadResult = downloadImage(imageUrl)

        if (downloadResult.isSuccess) {
            openPhotosApp()
        }

        return downloadResult
    }

    override suspend fun downloadImage(imageUrl: String): Result<Unit> {
        val imageResult = downloadBitmapImage(imageUrl)

        if (imageResult.isFailure) {
            return Result.failure(imageResult.exceptionOrNull() ?: Exception("Failed to download image."))
        }

        val uiImage = imageResult.getOrThrow()

        return withContext(Dispatchers.Main) {
            val currentStatus = suspendCancellableCoroutine { continuation ->
                val status = PHPhotoLibrary.authorizationStatus()

                if (status == PHAuthorizationStatusNotDetermined) {
                    PHPhotoLibrary.requestAuthorization { newStatus ->
                        continuation.resume(newStatus)
                    }
                } else {
                    continuation.resume(status)
                }
            }

            if (currentStatus != PHAuthorizationStatusAuthorized &&
                currentStatus != PHAuthorizationStatusLimited) {
                showPermissionDeniedAlert()
                return@withContext Result.failure(Exception("Photo access not granted"))
            }

            suspendCancellableCoroutine { continuation ->
                PHPhotoLibrary.sharedPhotoLibrary().performChanges({
                    PHAssetChangeRequest.creationRequestForAssetFromImage(uiImage)
                }) { success, error ->
                    if (success) {
                        continuation.resume(Result.success(Unit))
                    } else {
                        val message = error?.localizedDescription ?: "Unknown error"
                        continuation.resume(Result.failure(Exception(message)))
                    }
                }
            }
        }
    }


    @OptIn(ExperimentalForeignApi::class)
    private suspend fun downloadBitmapImage(imageUrl: String): Result<UIImage> = withContext(Dispatchers.IO) {
        val httpClient = HttpClient(Darwin)
        return@withContext try {
            val response = httpClient.get(imageUrl)
            val imageBytes = response.readRawBytes()
            val nsData = imageBytes.usePinned { pinned ->
                NSData.dataWithBytes(pinned.addressOf(0), imageBytes.size.toULong())
            }
            val uiImage = UIImage(nsData)
            Result.success(uiImage)
        } catch (e: Exception) {
            Result.failure(e)
        } finally {
            httpClient.close()
        }
    }

    private fun openPhotosApp() {
        val url = NSURL(string = "photos-redirect://")
        if (UIApplication.sharedApplication.canOpenURL(url)) {
            UIApplication.sharedApplication.openURL(url)
        } else {
            showWallpaperInstructionAlert()
        }
    }

    private fun showPermissionDeniedAlert() {
        val alert = UIAlertController.alertControllerWithTitle(
            title = "Permission Denied",
            message = "Please enable Photo Library access in Settings to save wallpapers.",
            preferredStyle = UIAlertControllerStyleAlert
        )
        alert.addAction(UIAlertAction.actionWithTitle("OK", style = UIAlertActionStyleDefault, handler = null))
        UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
            alert,
            animated = true,
            completion = null
        )
    }

    private fun showWallpaperInstructionAlert() {
        val alert = UIAlertController.alertControllerWithTitle(
            title = "Wallpaper Saved!",
            message = "The image has been saved to your Photos. Please go to the Photos app or Settings to set it as your wallpaper.",
            preferredStyle = UIAlertControllerStyleAlert
        )
        alert.addAction(UIAlertAction.actionWithTitle("OK", style = UIAlertActionStyleDefault, handler = null))
        UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
            alert,
            animated = true,
            completion = null
        )
    }
}