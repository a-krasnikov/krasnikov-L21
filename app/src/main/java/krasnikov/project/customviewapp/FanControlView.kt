package krasnikov.project.customviewapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import kotlin.math.atan2
import kotlin.math.min

class FanControlView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr), GestureDetector.OnGestureListener {

    private val gestureDetector: GestureDetector by lazy { GestureDetector(context, this) }

    private val textPaint by lazy {
        Paint().apply {
            color = Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = 24 * resources.displayMetrics.scaledDensity
        }
    }

    private var rotationDegrees = 0f

    private val percent
        get() = (rotationDegrees / 3.6).toInt()

    var rotateListener: (percentage: Int) -> Unit = {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = min(measuredWidth, measuredHeight)
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        drawText(canvas)
        drawButton(canvas)

        super.onDraw(canvas)
    }

    private fun drawButton(canvas: Canvas) {
        var drawable = VectorDrawableCompat
            .create(resources, R.drawable.ic_fan_controller, null)
            .apply {
                this?.setBounds(0, 0, canvas.width, canvas.height)
            }

        val centerX = width / 2f
        val centerY = height / 2f

        canvas.translate(centerX, centerY)
        canvas.rotate(rotationDegrees)
        canvas.translate(-centerX, -centerY)
        drawable?.draw(canvas)
    }

    private fun drawText(canvas: Canvas) {
        val x = width / 2f
        val y = height / 2 - (textPaint.descent() + textPaint.ascent()) / 2

        canvas.drawText("$percent%", x, y, textPaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (gestureDetector.onTouchEvent(event)) true
        else super.onTouchEvent(event);
    }

    override fun onScroll(
        e1: MotionEvent,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        val x = e2.x / width
        val y = e2.y / height
        rotationDegrees = cartesianToPolar(1 - x, 1 - y)

        return if (!rotationDegrees.isNaN()) {
            // instead of getting [0, 180], [-180, 0], we go for [0, 360]
            if (rotationDegrees < 0) rotationDegrees += 360

            // redraw
            postInvalidateOnAnimation()

            // get position percent
            rotateListener(percent)

            true
        } else
            false
    }

    private fun cartesianToPolar(x: Float, y: Float): Float {
        return -Math.toDegrees(atan2(x - 0.5, y - 0.5)).toFloat()
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        return true
    }

    override fun onShowPress(p0: MotionEvent?) {
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return true
    }

    override fun onLongPress(p0: MotionEvent?) {
    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return false
    }
}