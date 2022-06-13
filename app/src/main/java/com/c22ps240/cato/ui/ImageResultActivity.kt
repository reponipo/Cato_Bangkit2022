package com.c22ps240.cato.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.c22ps240.cato.R
import com.c22ps240.cato.api.ImageResponse
import com.c22ps240.cato.api.RetrofitApiConfig
import com.c22ps240.cato.databinding.ActivityImageResultBinding
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ImageResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageResultBinding
    private var getFile: File? = null

    companion object {
        const val TAG = "ImageResultActivity"

        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.camButton.setOnClickListener{
            val intent = Intent(this, CameraActivity::class.java)
            launcherIntentCameraX.launch(intent)

        }
        binding.analyzeButton.setOnClickListener{
            uploadImage()
        }

        binding.btnBack.setOnClickListener{
            val it = Intent(this, MainActivity::class.java)
            startActivity(it)
            finish()
        }

    }


    private fun uploadImage() {
        showProgressBar(true)

        if (getFile != null) {
            val file = reduceFileImage(getFile as File)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image",
                file.name,
                requestImageFile
            )


                    val client = RetrofitApiConfig.getApiService().postImage(imageMultipart)


                    client.enqueue(object : Callback<ImageResponse> {
                        override fun onResponse(
                            call: Call<ImageResponse>,
                            response: Response<ImageResponse>
                        ) {
                            showProgressBar(false)
                            val responseBody = response.body()
                            Log.d(TAG, "onResponse: $response")
                            if (response.isSuccessful) {
                                Toast.makeText(
                                    this@ImageResultActivity,
                                    getString(R.string.upload_success),
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(this@ImageResultActivity, ResultActivity::class.java)
                                startActivity(intent)
                            } else {
                                Log.e(TAG, "onFailure1: ${response.message()}")
                                Toast.makeText(
                                    this@ImageResultActivity,
                                    getString(R.string.upload_failed),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                            showProgressBar(false)
                            Log.e(TAG, "onFailure2: ${t.message}")
                            Toast.makeText(
                                this@ImageResultActivity,
                                getString(R.string.upload_failed),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                }
            }





    val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            getFile = myFile

            val result = rotateBitmap(
                BitmapFactory.decodeFile(getFile?.path),
                isBackCamera
            )

            binding.previewImageView.setImageBitmap(result)
            showButtonAnalyze(true)
        }
    }

    private fun showButtonAnalyze(status: Boolean){
        if (status) {
            binding.analyzeButton.visibility = View.VISIBLE
        } else {
            binding.analyzeButton.visibility = View.GONE
        }
    }

    private fun showProgressBar(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}