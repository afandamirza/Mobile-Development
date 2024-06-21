package com.bangkit.recyclopedia.view.homepage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.recyclopedia.R
import com.bangkit.recyclopedia.data.pref.UserPreference
import com.bangkit.recyclopedia.data.pref.dataStore
import com.bangkit.recyclopedia.databinding.ActivityHomeBinding
import com.bangkit.recyclopedia.view.ViewModelFactory
import coil.load
import coil.transform.CircleCropTransformation
import com.bangkit.recyclopedia.view.profile.ProfileActivity
import com.bangkit.recyclopedia.view.takephoto.TakePhotoActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
//    private lateinit var homeViewModel: HomeViewModel

    private val recycleListAdapter: HomeAdapter by lazy { HomeAdapter() }



    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }


    private fun initView() {
        binding.apply {
            supportActionBar?.apply {
                title = getString(R.string.app_name)
            }

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            toolbarAvatar.apply {
                load(R.drawable.user_image) {
                    crossfade(true)
                    placeholder(R.drawable.user_image)
                    error(R.drawable.user_image)
                    transformations(CircleCropTransformation())
                }
                setOnClickListener {
                    val intent = Intent(this@HomeActivity, ProfileActivity::class.java)
                    startActivity(intent)
                }
            }

            rvRecycleList.apply {
                layoutManager = object : LinearLayoutManager(this@HomeActivity) {
                    override fun canScrollVertically(): Boolean {
                        return false
                    }
                }
                setHasFixedSize(false)
                adapter = recycleListAdapter

                recycleListAdapter.submitList(listOf(
                    RecycleItem(R.drawable.image_kardus_kotak, "Kardus", "Kardus adalah jenis sampah yang berasal dari bahan kertas yang tebal dan kuat. Agar alam bisa menguraikan atau menghancurkan kardus diperlukan waktu yang lama, kardus membutuhkan waktu sekitar 5 bulan untuk dapat terurai di alam. Untuk mengurangi volume sampah kardus, kardus dapat diolah dengan cara daur ulang. Fakta menarik dari kardus Kardus yang didaur ulang bisa digunakan kembali untuk membuat kardus baru, kertas tulis, dan produk kertas lainnya. Setiap ton kardus yang didaur ulang dapat menghemat 17 pohon dan 7000 galon air."),
                    RecycleItem(R.drawable.image_kertas_daur_ulang, "Kertas", "Kertas adalah jenis sampah yang berasal dari serat kayu atau bahan organik lainnya yang diolah menjadi lembaran tipis. Kertas sangat umum digunakan dalam kehidupan sehari-hari, seperti untuk mencetak, menulis, atau sebagai bahan kemasan. Agar alam bisa menguraikan atau menghancurkan kertas diperlukan waktu yang bervariasi, biasanya sekitar 2 – 5 bulan untuk dapat terurai di alam. Untuk mengurangi volume sampah kertas, kertas dapat diolah dengan cara daur ulang. Fakta menarik dari kertas: Kertas yang didaur ulang bisa digunakan kembali untuk membuat kertas baru, tisu, dan produk kertas lainnya. Setiap ton kertas yang didaur ulang dapat menghemat sekitar 17 pohon, 380 galon minyak, dan 7000 galon air."),
                    RecycleItem(R.drawable.image_kaca_, "Botol Kaca", "Botol kaca adalah jenis sampah yang berasal dari bahan kaca yang diproduksi melalui peleburan pasir, natrium karbonat, dan batu kapur. Kaca sangat umum digunakan sebagai wadah minuman, makanan, dan berbagai produk lainnya karena sifatnya yang tahan lama dan tidak bereaksi dengan isinya. Agar alam bisa menguraikan atau menghancurkan botol kaca diperlukan waktu yang sangat lama, hingga 100 juta tahun. Untuk mengurangi volume sampah botol kaca, kaca dapat diolah dengan cara daur ulang. Fakta menarik dari botol kaca: kaca yang didaur ulang selalu menjadi bagian dari bahan untuk membuat wadah kaca yang baru. Kaca juga dapat digunakan kembali secara berulang-ulang tanpa kehilangan kualitas. Daur ulang satu ton kaca dapat menghemat sekitar 42 kilowatt jam energi dan mengurangi polusi udara sebesar 20%."),
                    RecycleItem(R.drawable.image_plastik, "Botol Plastik", "Botol plastik adalah jenis sampah yang berasal dari bahan polimer sintetis seperti PET (polyethylene terephthalate), HDPE (high-density polyethylene), dan jenis plastik lainnya. Botol plastik banyak digunakan sebagai wadah minuman, produk pembersih, dan berbagai cairan lainnya karena sifatnya yang ringan, kuat, dan tahan lama. Agar alam bisa menguraikan atau menghancurkan botol plastik diperlukan waktu yang sangat lama, biasanya sekitar 450 tahun atau lebih. Untuk mengurangi volume sampah botol plastik, plastik dapat diolah dengan cara daur ulang. Fakta menarik dari botol plastik: Daur ulang satu ton plastik dapat menghemat sekitar 5.774 kWh energi, 16,3 barel minyak, 30 meter kubik ruang tempat pembuangan akhir, dan mengurangi emisi karbon dioksida sebanyak 2 ton."),
                    RecycleItem(R.drawable.image_kaleng, "Kaleng", "Kaleng minuman adalah jenis sampah yang berasal dari bahan aluminium atau kadang-kadang baja yang digunakan sebagai wadah minuman ringan, jus, bir, dan berbagai minuman lainnya. Kaleng minuman sangat populer karena ringan, mudah didaur ulang, dan memiliki kemampuan untuk menjaga kesegaran isi minuman. Agar alam bisa menguraikan atau menghancurkan kaleng minuman diperlukan waktu yang sangat lama, bahkan hingga 200 – 500 tahun. Untuk mengurangi volume sampah kaleng minuman, kaleng dapat diolah dengan cara daur ulang. Fakta menarik dari kaleng minuman: Daur ulang satu ton aluminium dapat menghemat sekitar 14.000 kWh energi, 40 barel minyak, dan mengurangi emisi karbon dioksida sebanyak 10 ton."),
                ))
            }

            btnCamera.setOnClickListener {
                val intent = Intent(this@HomeActivity, TakePhotoActivity::class.java)
                intent.putExtra(TakePhotoActivity.EXTRA_IS_TRASH, true)
                startActivity(intent)
            }

            btnRecycle.setOnClickListener {
                val intent = Intent(this@HomeActivity, TakePhotoActivity::class.java)
                intent.putExtra(TakePhotoActivity.EXTRA_IS_TRASH, false)
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).apply {
            setTitle("Keluar Aplikasi")
            setMessage("Apakah Anda yakin ingin keluar dari aplikasi?")
            setPositiveButton("Ya") { _, _ ->
                super.onBackPressed()
                finishAffinity()
            }
            setNegativeButton("Tidak") { _, _ -> }
            create()
            show()
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_bar, menu)
//        return super.onCreateOptionsMenu(menu)
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.logout -> {
//                AlertDialog.Builder(this).apply {
//                    setTitle("Logout Confirmation")
//                    setMessage("Are you sure you want to logout from your account?")
//                    setPositiveButton("Yes") { _, _ ->
//                        homeViewModel.logout()
//                        val intent = Intent(context, WelcomePageActivity::class.java)
//                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                        startActivity(intent)
//                        finish()
//                    }
//                    setNegativeButton("No") { _, _ -> }
//                    create()
//                    show()
//                }
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }


//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
//            val selectedImageUri = data?.data
//            selectedImageUri?.let {
//                uploadImageToServer(it)
//            }
//        }
//    }
//
//    private fun uploadImageToServer(fileUri: Uri) {
//        val file = File(getRealPathFromURI(fileUri))
//        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
//        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
//        val description = RequestBody.create("text/plain".toMediaTypeOrNull(), "image description")
//
//        val call = ApiConfig.getApiService().uploadImage(body, description)
//        call.enqueue(object : Callback<ImagePredictionResponse> {
//            override fun onResponse(call: Call<ImagePredictionResponse>, response: Response<ImagePredictionResponse>) {
//                if (response.isSuccessful) {
//                    val predictionResponse = response.body()
//                    predictionResponse?.let {
//                        Toast.makeText(this@HomeActivity, it.message, Toast.LENGTH_SHORT).show()
//                        // Display prediction result or handle it as needed
//                    }
//                } else {
//                    Toast.makeText(this@HomeActivity, "Prediction failed", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<ImagePredictionResponse>, t: Throwable) {
//                Toast.makeText(this@HomeActivity, t.message, Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    private fun getRealPathFromURI(uri: Uri): String {
//        var path = ""
//        val projection = arrayOf(MediaStore.Images.Media.DATA)
//        val cursor = contentResolver.query(uri, projection, null, null, null)
//        cursor?.use {
//            if (it.moveToFirst()) {
//                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//                path = it.getString(columnIndex)
//            }
//        }
//        return path
//    }
}