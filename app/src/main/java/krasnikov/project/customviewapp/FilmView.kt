package krasnikov.project.customviewapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class FilmView @JvmOverloads constructor(context: Context, attrs: AttributeSet) :
    LinearLayout(context, attrs) {

    private val ivPoster: ImageView by lazy { findViewById(R.id.ivPoster) }
    private val tvName: TextView by lazy { findViewById(R.id.tvName) }
    private val tvGenre: TextView by lazy { findViewById(R.id.tvGenre) }
    private val tvCost: TextView by lazy { findViewById(R.id.tvCost) }
    private val rating: RatingBar by lazy { findViewById(R.id.rating) }

    init {
        inflate(context, R.layout.film_view, this)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.FilmView)
        ivPoster.setImageDrawable(attributes.getDrawable(R.styleable.FilmView_poster))
        tvName.text = attributes.getString(R.styleable.FilmView_name)
        tvGenre.text = attributes.getString(R.styleable.FilmView_genre)
        tvCost.text = attributes.getString(R.styleable.FilmView_cost)
        rating.rating = attributes.getFloat(R.styleable.FilmView_rating, 0f)

        attributes.recycle()
    }

    fun setPoster(drawable: Drawable) {
        ivPoster.setImageDrawable(drawable)
    }

    fun setPoster(@DrawableRes resId: Int) {
        ivPoster.setImageResource(resId)
    }

    fun setPoster(bm: Bitmap) {
        ivPoster.setImageBitmap(bm)
    }

    fun setName(text: CharSequence) {
        tvName.text = text
    }

    fun setName(@StringRes resId: Int) {
        tvName.setText(resId)
    }

    fun setGenre(text: CharSequence) {
        tvGenre.text = text
    }

    fun setGenre(@StringRes resId: Int) {
        tvGenre.setText(resId)
    }

    fun setCost(text: CharSequence) {
        tvCost.text = text
    }

    fun setCost(@StringRes resId: Int) {
        tvCost.setText(resId)
    }

    fun setRating(value: Float) {
        rating.rating = value
    }
}