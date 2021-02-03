package krasnikov.project.customviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RatingBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<FilmView>(R.id.film).apply {
            setCost("UAH 20.00")
            setGenre("Animation")
            setName("The Boss Baby")
            setPoster(R.drawable.boss_baby_poster)
            setRating(3f)
        }
    }
}