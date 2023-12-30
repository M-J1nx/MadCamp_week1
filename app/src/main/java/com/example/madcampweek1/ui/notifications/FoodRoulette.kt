package com.example.madcampweek1.ui.notifications

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import java.util.jar.Attributes

class FoodRoulette @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val rectF= RectF()
    private val strokePaint = Paint()
    private val fillPaint = Paint()

    private var rouletteSize = 2

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
            val colors = listOf("#fe4a49", "#2ab7ca", "#fed766", "#e6e6ea", "#f6abb6", "#005b96", "#7bc043", "#f37735")

            for (i in 0 until rouletteSize) {
                fillPaint.color = Color.parseColor(colors[i])

                val startAngle = if (i == 0) 0f else sweepAngle * i
                canvas?.drawArc(rectF, startAngle, sweepAngle, true, fillPaint)
            }
        } else throw RuntimeException("size out of roulette")
    }
}