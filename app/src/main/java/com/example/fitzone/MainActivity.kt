package com.example.fitzone
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
class MainActivity : AppCompatActivity() {
    private var page = 0
    private lateinit var cards: List<ImageView>
    private lateinit var pageLabel: TextView
    private val gifs by lazy { (1..12).map { resources.getIdentifier("ejercicio_$it", "drawable", packageName) } }
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); setContentView(R.layout.activity_main)
        cards = listOf(R.id.exercise_1, R.id.exercise_2, R.id.exercise_3, R.id.exercise_4).map { findViewById(it) }; pageLabel = findViewById(R.id.page_label)
        findViewById<Button>(R.id.previous_page).setOnClickListener { if (page > 0) showPage(page - 1) }; findViewById<Button>(R.id.next_page).setOnClickListener { if (page < 2) showPage(page + 1) }; showPage(0)
    }
    private fun showPage(newPage: Int) { page = newPage; cards.forEachIndexed { slot, card -> val resourceId = gifs[page * 4 + slot]; card.visibility = if (resourceId != 0) ImageView.VISIBLE else ImageView.INVISIBLE; if (resourceId != 0) Glide.with(this).load(resourceId).into(card); card.setOnClickListener { startActivity(Intent(this, WorkoutActivity::class.java).putExtra(WorkoutActivity.GIF_RES_ID, resourceId)) } }; pageLabel.text = getString(R.string.page_format, page + 1); findViewById<Button>(R.id.previous_page).isEnabled = page > 0; findViewById<Button>(R.id.next_page).isEnabled = page < 2; cards.firstOrNull { it.visibility == ImageView.VISIBLE }?.requestFocus() }
}
