package krasnikov.project.customviewapp

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import kotlin.math.min

class FanView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private var rotationDegrees = 0f

    private val isActive
        get() = speed > 0

    var speed: Float = 0f
        set(value) {
            field = value
            if (isActive)
                postInvalidateOnAnimation()
        }

    private val canvasPaddingPx = (20 * resources.displayMetrics.density).toInt()

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.FanView)
        speed = attributes.getFloat(R.styleable.FanView_speed, 0f)

        attributes.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = min(measuredWidth, measuredHeight)
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        drawFan(canvas)
        if (isActive) {
            postInvalidateOnAnimation()
        }
        super.onDraw(canvas)
    }

    private fun drawFan(canvas: Canvas) {
        var drawable = VectorDrawableCompat.create(resources, R.drawable.ic_fan, null)
            .apply {
                this?.setBounds(
                    canvasPaddingPx,
                    canvasPaddingPx,
                    canvas.width - canvasPaddingPx,
                    canvas.height - canvasPaddingPx
                )
            }

        val centerX = width / 2f
        val centerY = height / 2f

        canvas.translate(centerX, centerY)
        canvas.rotate(rotation())
        canvas.translate(-centerX, -centerY)
        drawable?.draw(canvas)
    }

    private fun rotation(): Float {
        rotationDegrees = (rotationDegrees + speed) % 360
        return rotationDegrees
    }
}