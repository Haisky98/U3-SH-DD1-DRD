package com.example.fitzone
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
class WorkoutActivity : AppCompatActivity() {
    companion object { const val GIF_RES_ID = "GIF_RES_ID" }
    private var timer: CountDownTimer? = null; private var remaining = 120_000L
    private lateinit var countdown: TextView; private lateinit var toggle: Button; private var running = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_workout)
        countdown = findViewById(R.id.countdown); toggle = findViewById(R.id.toggle_timer)
        Glide.with(this).load(intent.getIntExtra(GIF_RES_ID, R.drawable.ejercicio_1)).into(findViewById<ImageView>(R.id.workout_image))
        toggle.setOnClickListener { if (running) pause() else startTimer() }
        findViewById<Button>(R.id.reset_timer).setOnClickListener { timer?.cancel(); remaining = 120_000L; running = false; updateTime(); toggle.text = getString(R.string.resume); toggle.isEnabled = true }
        updateTime(); startTimer()
    }
    private fun startTimer() { timer?.cancel(); running = true; toggle.text = getString(R.string.pause); timer = object : CountDownTimer(remaining, 1_000) {
        override fun onTick(ms: Long) { remaining = ms; updateTime() }
        override fun onFinish() { remaining = 0; running = false; updateTime(); toggle.text = getString(R.string.finished); toggle.isEnabled = false }
    }.start() }
    private fun pause() { timer?.cancel(); running = false; toggle.text = getString(R.string.resume) }
    private fun updateTime() { countdown.text = getString(R.string.timer_format, remaining / 60_000, (remaining / 1_000) % 60) }
    override fun onDestroy() { timer?.cancel(); super.onDestroy() }
}
