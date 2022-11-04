package id.melur.gliandroidtest

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util
import id.melur.gliandroidtest.databinding.ActivityMainBinding
import id.melur.gliandroidtest.databinding.ActivityVideoHandleBinding

class VideoHandleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoHandleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoHandleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.googleBtn.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.youtube.com/watch?v=JaV7mmc9HGw")
            startActivity(openURL)
        }

        binding.tutorialkartBtn.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.tutorialkart.com/")
            startActivity(openURL)
        }
    }
}