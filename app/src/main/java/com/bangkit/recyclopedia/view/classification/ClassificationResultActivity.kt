package com.bangkit.recyclopedia.view.classification

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.recyclopedia.R
import com.bangkit.recyclopedia.databinding.ActivityResultClassificationBinding
import com.bangkit.recyclopedia.view.history.HistoryActivity
import com.bangkit.recyclopedia.view.takephoto.TakePhotoActivity

class ClassificationResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultClassificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultClassificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        displayResult()
    }

    private fun initView() {
        binding.apply {
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            rvTutorial.apply {
                val tutorialListAdapter = ClassificationResultAdapter(context)
                layoutManager = LinearLayoutManager(this@ClassificationResultActivity, LinearLayoutManager.HORIZONTAL, false)
                adapter = tutorialListAdapter
                tutorialListAdapter.submitList(
                    listOf(
                        R.drawable.image_video_1,
                        R.drawable.image_video_2,
                        R.drawable.image_plastik,
                        R.drawable.image_cardboard
                    )
                )
            }

            btnRecycling.setOnClickListener {
                val intent = Intent(this@ClassificationResultActivity, TrashActionActivity::class.java)
                intent.putExtra(TakePhotoActivity.EXTRA_IS_TRASH, false)
                intent.putExtra(TrashActionActivity.EXTRA_ACTION, TrashActionActivity.ACTION_RECYCLING)
                startActivity(intent)
            }

            btnThrowAway.setOnClickListener {
                val intent = Intent(this@ClassificationResultActivity, TrashActionActivity::class.java)
                intent.putExtra(TakePhotoActivity.EXTRA_IS_TRASH, true)
                intent.putExtra(TrashActionActivity.EXTRA_ACTION, TrashActionActivity.ACTION_THROW_AWAY)
                startActivity(intent)
            }

            binding.back.setOnClickListener {
                onBackPressed()
            }


        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun displayResult() {
        val result = intent.getStringExtra("result")
        val congrats = intent.getStringExtra("message")

        val kardusBahanFormattedText = """
            <h2>Alat dan Bahan:</h2>
            <ul>
                <li> 2 kotak kardus dengan ukuran yang sama</li>
                <li> Selotip</li>
                <li> Gunting</li>
            </ul>
        """.trimIndent()

        val kardusInfruksiFormattedText = """
            <h2>Cara Membuat:</h2>
            <ol>
                <li> 1) Untuk membuat kerajinan tangan dari kardus ini, potong sudut setiap kotak untuk membuat selembar kardus datar.</li>
                <li> 2) Perkuat lembaran kardus dengan merekatkan lipatan dan celahnya.</li>
                <li> 3) Kemudian, buat gambar garis hidung roket yang lurus di bagian atas kotak kardus yang diratakan. Potong menjadi bentuk roket.</li>
                <li> 4) Tempelkan semua bagian untuk memberikan bentuk silinder menggunakan selotip. Terakhir tempelkan hidung roket.</li>
                <li> 5) Kemudian buatlah sayap roket menggunakan potongan karton dan tempelkan di bagian bawah roket.</li>
            </ol>
        """.trimIndent()

        val kacaBahanFormattedText = """
            <h3>Alat dan Bahan:</h3>
            <ul>
                <li> 2 buah ram ring (lingkaran untuk kerajinan menyulam) dengan ukuran berbeda</li>
                <li> Cat</li>
                <li> Benang tebal</li>
                <li> Potongan kaca berwarna-warni atau manik-manik kaca</li>
            </ul>
        """.trimIndent()

        val kacaInfruksiFormattedText = """
            <h2>Cara Membuat:</h2>
            <ol>
                <li> 1) Cat ram ring sesuai keinginan dan biarkan kering.</li>
                <li> 2) Ikat ram ring satu sama lain, dengan lingkaran kecil berada di dalam lingkaran besar. Pastikan tali terpasang kencang agar kedua lingkaran terikat kuat.</li>
                <li> 3) Buat tali yang menjuntai ke bawah pada masing-masing lingkaran. Tali ini akan digunakan untuk mengikat hiasan lonceng angin.</li>
                <li> 4) Ikat potongan kaca atau manik kaca pada tali yang menjuntai. Gunakan berbagai ukuran dan warna untuk hasil yang lebih menarik.</li>
                <li> 5) Setelah selesai, gantung lonceng angin di jendela atau tempat lain sesuai keinginan.</li>
            </ol>
        """.trimIndent()

        val kalengBahanFormattedText = """
            <h3>Alat dan Bahan:</h3>
            <ul>
              <li>Kaleng kosong</li>
              <li>Cat akrilik</li>
              <li>Manik-manik untuk hiasan kaleng (opsional)</li>
              <li>Gunting</li>
              <li>Kuas</li>
              <li>Pembuka kaleng</li>
            </ul>
        """.trimIndent()

        val kalengInfruksiFormattedText = """
            <h2>Cara Membuat:</h2>
            <ol>
              <li> 1) Ambil kaleng kosong yang telah disiapkan dan lepaskan salah satu tutupnya dengan pembuka kaleng.</li>
              <li> 2) Bersihkan bagian dalam dan luar kaleng dengan sabun cuci piring, lalu keringkan.</li>
              <li> 3) Setelah kering, aplikasikan dua lapis cat akrilik dan biarkan lapisan cat mengering.</li>
              <li> 4) Tambahkan hiasan manik-manik. (opsional)</li>
              <li> 5) Jika sudah selesai menghias, masukkan bunga yang diinginkan.</li>
            </ol>
        """.trimIndent()

        val kertasBahanFormattedText = """
            <h3>Alat dan Bahan:</h3>
            <ul>
              <li>Kertas bekas</li>
              <li>Gunting</li>
              <li>Lem</li>
              <li>Cat (opsional)</li>
              <li>Kertas kardus (opsional)</li>
              <li>Manik-manik atau hiasan lainnya (opsional)</li>
            </ul>
        """.trimIndent()

        val kertasInfruksiFormattedText = """
            <h2>Cara Membuat:</h2>
            <ol>
                <li> 1) Potong kertas kardus menjadi dua bagian, untuk bagian depan dan bagian belakang bingkai foto. <li/>
                <li> 2) Setelah dua bagian kardus terpotong rapi, selanjutnya adalah memotong bagian tengah salah satu kardus yang akan dijadikan tempat foto. <li/> 
                <li> 3) Selanjutnya, buat garis 1,5 cm dari setiap sisi kardus untuk membentuk lubang persegi panjang atau bisa menempelkan foto di kardus dan buat garis mengikuti ukuran foto tersebut. <li/>          
                <li> 4) Setelah selesai dengan kertas kardus sebagai dasarnya, selanjutnya siapkan kertas bekas, dan buat menjadi lintingan kecil yang memanjang sekitar 20 hingga 30 lintingan. <li/>               
                <li> 5) Kemudian, tempelkan lintingan kertas tadi ke bagian depan bingkai foto menggunakan lem. <li/>
                <li> 6) Langkah terakhir, dapat menambahkan hiasan bunga atau manik-manik di pada bagian pinggir bingkai. <li/>
            </ol>
        """.trimIndent()

        val plastikBahanFormattedText = """
            <h3>Alat dan Bahan:</h3>
            <ul>
              <li>Botol plastik bekas berukuran kecil, sedang, atau besar</li>
              <li>Penggaris</li>
              <li>Pulpen atau spidol</li>
              <li>Gunting</li>
              <li>Cutter</li>
            </ul>
        """.trimIndent()

        val plastikInfruksiFormattedText = """
            <h2>Cara Membuat:</h2>
            <ol>
                <li> 1) Potong botol plastik menjadi dua bagian. Bagian bawah botol akan digunakan untuk membuat vas bunga. <li/>
                <li> 2) Buat garis vertikal dengan jarak dan panjang yang sama di sekeliling botol, lalu gunting sesuai garis tersebut. <li/>
                <li> 3) Setelah dipotong, letakkan botol dalam posisi terbalik dan tekan kuat sehingga potongan melebar. <li/>
                <li> 4) Tekuk potongan ke arah samping secara perlahan dan selipkan ujung potongan ke sisi dalam bagian di sebelahnya. <li/>
                <li> 5) Lakukan pada seluruh potongan di sekeliling botol plastik. <li/>
            </ol>
        """.trimIndent()


        Log.d(TAG, "$result, Message: $congrats")

        when (result) {
            "cardboard" -> {
                binding.result.text = "KARDUS"
                binding.judulIde.text = "Mainan Roket"
                binding.fact.text = "Kardus adalah jenis sampah yang berasal dari bahan kertas yang tebal dan kuat. Agar alam bisa menguraikan atau menghancurkan kardus diperlukan waktu yang lama, kardus membutuhkan waktu sekitar 5 bulan untuk dapat terurai di alam. Untuk mengurangi volume sampah kardus, kardus dapat diolah dengan cara daur ulang. Fakta menarik dari kardus Kardus yang didaur ulang bisa digunakan kembali untuk membuat kardus baru, kertas tulis, dan produk kertas lainnya. Setiap ton kardus yang didaur ulang dapat menghemat 17 pohon dan 7000 galon air."
                binding.textAlatDanBahan.text = Html.fromHtml(kardusBahanFormattedText, Html.FROM_HTML_MODE_LEGACY)
                binding.textIntruksi.text = Html.fromHtml(kardusInfruksiFormattedText, Html.FROM_HTML_MODE_LEGACY)
                binding.ideGambar.setImageResource(R.drawable.kardus_roket)

            }
            "glass" -> {
                binding.result.text = "BOTOL KACA"
                binding.judulIde.text = "Lonceng Angin"
                binding.fact.text = "Botol kaca adalah jenis sampah yang berasal dari bahan kaca yang diproduksi melalui peleburan pasir, natrium karbonat, dan batu kapur. Kaca sangat umum digunakan sebagai wadah minuman, makanan, dan berbagai produk lainnya karena sifatnya yang tahan lama dan tidak bereaksi dengan isinya. Agar alam bisa menguraikan atau menghancurkan botol kaca diperlukan waktu yang sangat lama, hingga 100 juta tahun. Untuk mengurangi volume sampah botol kaca, kaca dapat diolah dengan cara daur ulang. Fakta menarik dari botol kaca: kaca yang didaur ulang selalu menjadi bagian dari bahan untuk membuat wadah kaca yang baru. Kaca juga dapat digunakan kembali secara berulang-ulang tanpa kehilangan kualitas. Daur ulang satu ton kaca dapat menghemat sekitar 42 kilowatt jam energi dan mengurangi polusi udara sebesar 20%."
                binding.textAlatDanBahan.text = Html.fromHtml(kacaBahanFormattedText, Html.FROM_HTML_MODE_LEGACY)
                binding.textIntruksi.text = Html.fromHtml(kacaInfruksiFormattedText, Html.FROM_HTML_MODE_LEGACY)
                binding.ideGambar.setImageResource(R.drawable.kaca_lonceng_angin)

            }
            "metal" -> {
                binding.result.text = "KALENG"
                binding.judulIde.text = "Pot Bunga"
                binding.fact.text = "Kaleng minuman adalah jenis sampah yang berasal dari bahan aluminium atau kadang-kadang baja yang digunakan sebagai wadah minuman ringan, jus, bir, dan berbagai minuman lainnya. Kaleng minuman sangat populer karena ringan, mudah didaur ulang, dan memiliki kemampuan untuk menjaga kesegaran isi minuman. Agar alam bisa menguraikan atau menghancurkan kaleng minuman diperlukan waktu yang sangat lama, bahkan hingga 200 – 500 tahun. Untuk mengurangi volume sampah kaleng minuman, kaleng dapat diolah dengan cara daur ulang. Fakta menarik dari kaleng minuman: Daur ulang satu ton aluminium dapat menghemat sekitar 14.000 kWh energi, 40 barel minyak, dan mengurangi emisi karbon dioksida sebanyak 10 ton."
                binding.textAlatDanBahan.text = Html.fromHtml(kalengBahanFormattedText, Html.FROM_HTML_MODE_LEGACY)
                binding.textIntruksi.text = Html.fromHtml(kalengInfruksiFormattedText, Html.FROM_HTML_MODE_LEGACY)
                binding.ideGambar.setImageResource(R.drawable.kaleng_pot)

            }
            "paper" -> {
                binding.result.text = "KERTAS"
                binding.judulIde.text = "Bingkai Foto"
                binding.fact.text = "Kertas adalah jenis sampah yang berasal dari serat kayu atau bahan organik lainnya yang diolah menjadi lembaran tipis. Kertas sangat umum digunakan dalam kehidupan sehari-hari, seperti untuk mencetak, menulis, atau sebagai bahan kemasan. Agar alam bisa menguraikan atau menghancurkan kertas diperlukan waktu yang bervariasi, biasanya sekitar 2 – 5 bulan untuk dapat terurai di alam. Untuk mengurangi volume sampah kertas, kertas dapat diolah dengan cara daur ulang. Fakta menarik dari kertas: Kertas yang didaur ulang bisa digunakan kembali untuk membuat kertas baru, tisu, dan produk kertas lainnya. Setiap ton kertas yang didaur ulang dapat menghemat sekitar 17 pohon, 380 galon minyak, dan 7000 galon air."
                binding.textAlatDanBahan.text = Html.fromHtml(kertasBahanFormattedText, Html.FROM_HTML_MODE_LEGACY)
                binding.textIntruksi.text = Html.fromHtml(kertasInfruksiFormattedText, Html.FROM_HTML_MODE_LEGACY)
                binding.ideGambar.setImageResource(R.drawable.kertas_bingkai)

            }
            "plastic" -> {
                binding.result.text = "BOTOL PLASTIK"
                binding.judulIde.text = "Vas Bunga"
                binding.fact.text = "Botol plastik adalah jenis sampah yang berasal dari bahan polimer sintetis seperti PET (polyethylene terephthalate), HDPE (high-density polyethylene), dan jenis plastik lainnya. Botol plastik banyak digunakan sebagai wadah minuman, produk pembersih, dan berbagai cairan lainnya karena sifatnya yang ringan, kuat, dan tahan lama. Agar alam bisa menguraikan atau menghancurkan botol plastik diperlukan waktu yang sangat lama, biasanya sekitar 450 tahun atau lebih. Untuk mengurangi volume sampah botol plastik, plastik dapat diolah dengan cara daur ulang. Fakta menarik dari botol plastik: Daur ulang satu ton plastik dapat menghemat sekitar 5.774 kWh energi, 16,3 barel minyak, 30 meter kubik ruang tempat pembuangan akhir, dan mengurangi emisi karbon dioksida sebanyak 2 ton."
                binding.textAlatDanBahan.text = Html.fromHtml(plastikBahanFormattedText, Html.FROM_HTML_MODE_LEGACY)
                binding.textIntruksi.text = Html.fromHtml(plastikInfruksiFormattedText, Html.FROM_HTML_MODE_LEGACY)
                binding.ideGambar.setImageResource(R.drawable.plastik_pot)

            }
        }

    }

    companion object {
        private const val TAG = "ClassificationResultActivity"
    }


}