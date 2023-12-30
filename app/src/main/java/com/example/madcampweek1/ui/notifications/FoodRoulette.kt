package com.example.madcampweek1.ui.notifications

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import java.util.jar.Attributes

class FoodRoulette @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val rectF = RectF()
    private val strokePaint = Paint()
    private val fillPaint = Paint()
    private val textPaint = Paint()

    private var rouletteSize = 6
    private var rouletteData = listOf("한식", "중식", "일식", "양식", "분식", "간편식")


    companion object {
        private const val DEFAULT_PADDING = 0f
    }

    interface RotateListener {
        fun onRotateStart()
        fun onRotateEnd(result: String)
    }

    init {
        strokePaint.apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 15f
            isAntiAlias = true
        }

        fillPaint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        textPaint.apply {
            color = Color.BLACK
            textSize = 60f
            textAlign = Paint.Align.CENTER
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val rectLeft = left.toFloat() + paddingLeft
        val rectRight = right - paddingRight
        val rectTop = height / 2f - rectRight / 2f + paddingTop
        val rectBottom = height / 2f + rectRight / 2f - paddingRight

        rectF.set(rectLeft, rectTop, rectRight.toFloat(), rectBottom)

        drawRoulette(canvas, rectF)
    }

    private fun drawRoulette(canvas: Canvas?, rectF: RectF) {
        canvas?.drawArc(rectF, 0f, 360f, true, strokePaint)

        if (rouletteSize in 2..8) {
            val sweepAngle = 360f / rouletteSize.toFloat()
            val colors = listOf("#86D3C3", "#CDFFF5")

            val centerX = (rectF.left + rectF.right)/2
            val centerY = (rectF.top + rectF.bottom)/2
            val radius = (rectF.right - rectF.left)/2*0.5

            var colorIndex=0
            for (i in 0 until rouletteSize) {

                fillPaint.color = Color.parseColor(colors[colorIndex])

                if (colorIndex+1>=colors.size) colorIndex=0
                else colorIndex++

                val startAngle = if (i == 0) 0f else sweepAngle * i
                canvas?.drawArc(rectF, startAngle, sweepAngle, true, fillPaint)

                val medianAngle = (startAngle + sweepAngle / 2f) * Math.PI / 180f
                val x = (centerX + (radius * Math.cos(medianAngle))).toFloat()
                val y = (centerY + (radius * Math.sin(medianAngle))).toFloat() + FoodRoulette.DEFAULT_PADDING

                canvas?.drawText(rouletteData[i], x, y, textPaint)

            }
        } else throw RuntimeException("size out of roulette")
    }

    fun rotateRoulette(toDegrees: Float, duration: Long, rotateListener: RotateListener?) {
        val animListener = object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {
                rotateListener?.onRotateStart()
            }

            override fun onAnimationEnd(animation: Animation?) {
                rotateListener?.onRotateEnd("<결과>")
            }
        }

        val rotateAnim = RotateAnimation(
            0f, toDegrees,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotateAnim.duration = duration
        rotateAnim.fillAfter = true

        startAnimation(rotateAnim)
    }
}