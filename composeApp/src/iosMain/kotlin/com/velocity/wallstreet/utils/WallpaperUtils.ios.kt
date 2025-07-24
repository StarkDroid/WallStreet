package com.velocity.wallstreet.utils

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.request.get
import io.ktor.client.statement.readRawBytes
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes
import platform.Photos.PHAssetChangeRequest
import platform.Photos.PHAuthorizationStatus
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHAuthorizationStatusDenied
import platform.Photos.PHAuthorizationStatusLimited
import platform.Photos.PHAuthorizationStatusNotDetermined
import platform.Photos.PHAuthorizationStatusRestricted
import platform.Photos.PHPhotoLibrary
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import kotlin.coroutines.resume

class WallpaperUtilsIOS: WallpaperFunctions {
    override suspend fun setWallpaper(
        imageUrl: String,
        type: WallpaperType
    ): Result<Unit> {
        val downloadResult = downloadImage(imageUrl)

        if (downloadResult.isSuccess) {
            showWallpaperInstructionAlert()
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
            val status = PHPhotoLibrary.authorizationStatus()

            val currentStatus: PHAuthorizationStatus = if (status == PHAuthorizationStatusNotDetermined) {
                suspendCancellableCoroutine { continuation: CancellableContinuation<PHAuthorizationStatus> ->
                    PHPhotoLibrary.requestAuthorization { newStatus: PHAuthorizationStatus ->
                        continuation.resume(newStatus)
                    }
                }
            } else {
                status
            }

            if (currentStatus != PHAuthorizationStatusAuthorized && currentStatus != PHAuthorizationStatusLimited) {
                val errorMessage = when (currentStatus) {
                    PHAuthorizationStatusDenied -> "Photo Library access denied by user."
                    PHAuthorizationStatusRestricted -> "Photo Library access restricted."
                    else -> "Photo Library access not authorized. Status: $currentStatus"
                }
                showPermissionDeniedAlert()
                return@withContext Result.failure(Exception(errorMessage))
            }

            suspendCancellableCoroutine { continuation ->
                PHPhotoLibrary.sharedPhotoLibrary().performChanges({
                    PHAssetChangeRequest.creationRequestForAssetFromImage(uiImage)
                }) { success, error ->
                    if (success) {
                        println("Image successfully saved to iOS Photo Library.")
                        continuation.resume(Result.success(Unit))
                    } else {
                        val errorMessage = error?.localizedDescription ?: "Unknown error saving image to Photo Library."
                        println("Error saving image to iOS Photo Library: $errorMessage")
                        continuation.resume(Result.failure(Exception(errorMessage)))
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
            val nsData = NSData.dataWithBytes(imageBytes.refTo(0) as COpaquePointer?, imageBytes.size.toULong())
            val uiImage = UIImage(nsData)
            Result.success(uiImage)
        } catch (e: Exception) {
            Result.failure(e)
        } finally {
            httpClient.close()
        }
    }

    private fun showPermissionDeniedAlert() {
        val alert = UIAlertController.alertControllerWithTitle(
            title = "Permission Denied",
            message = "Please enable Photo Library access in Settings to save wallpapers.",
            preferredStyle = UIAlertControllerStyleAlert
        )
        alert.addAction(UIAlertAction.actionWithTitle("OK", style = UIAlertActionStyleDefault, handler = null))
        UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(alert, animated = true, completion = null)
    }

    private fun showWallpaperInstructionAlert() {
        val alert = UIAlertController.alertControllerWithTitle(
            title = "Wallpaper Saved!",
            message = "The image has been saved to your Photos. Please go to the Photos app or Settings to set it as your wallpaper.",
            preferredStyle = UIAlertControllerStyleAlert
        )
        alert.addAction(UIAlertAction.actionWithTitle("OK", style = UIAlertActionStyleDefault, handler = null))
        UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(alert, animated = true, completion = null)
    }
}