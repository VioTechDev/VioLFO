package com.ltl.apero.languageopen.language.widget

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.AccelerateDecelerateInterpolator
import com.ltl.apero.languageopen.R
import com.ltl.apero.languageopen.language.model.ToggleableView

/**
 *
 *
 * Created by VinhVox on 15/12/23.
 *
 */
class LabeledSwitch : ToggleableView {
    private var padding = 0
    private var colorOn = 0
    private var colorOff = 0
    private var colorTextOff = 0
    private var colorTextOn = 0
    private var colorBorder = 0
    private var colorDisabled = 0
    private var textSize = 0
    private var outerRadii = 0
    private var thumbRadii = 0
    private var paint: Paint? = null
    private var startTime: Long = 0
    private var labelOn: String? = null
    private var labelOff: String? = null
    private var thumbBounds: RectF? = null
    private var leftBgArc: RectF? = null
    private var rightBgArc: RectF? = null
    private var leftFgArc: RectF? = null
    private var rightFgArc: RectF? = null

    /**
     *
     * Returns the typeface for Switch on/off labels.
     *
     * @return the typeface for Switch on/off labels..
     */
    var typeface: Typeface? = null
        /**
         *
         * Changes the typeface for Switch on/off labels.
         *
         * @param typeface the typeface for Switch on/off labels.
         */
        set(typeface) {
            field = typeface
            paint!!.typeface = typeface
            invalidate()
        }
    private var thumbOnCenterX = 0f
    private var thumbOffCenterX = 0f

    /**
     * Simple constructor to use when creating a switch from code.
     * @param context The Context the switch is running in, through which it can
     * access the current theme, resources, etc.
     */
    constructor(context: Context?) : super(context) {
        initView()
    }

    /**
     * Constructor that is called when inflating a switch from XML.
     *
     * @param context The Context the switch is running in, through which it can
     * access the current theme, resources, etc.
     * @param attrs The attributes of the XML tag that is inflating the switch.
     */
    constructor(context: Context?, attrs: AttributeSet) : super(context, attrs) {
        initView()
        initProperties(attrs)
    }

    /**
     * Perform inflation from XML and apply a class-specific base style from a
     * theme attribute.
     *
     * @param context The Context the switch is running in, through which it can
     * access the current theme, resources, etc.
     * @param attrs The attributes of the XML tag that is inflating the switch.
     * @param defStyleAttr An attribute in the current theme that contains a
     * reference to a style resource that supplies default values for
     * the switch. Can be 0 to not look for defaults.
     */
    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
        initProperties(attrs)
    }

    private fun initView() {
        this.isOn = false
        labelOn = "ON"
        labelOff = "OFF"
        this.enabled = true
        textSize = (12f * resources.displayMetrics.scaledDensity).toInt()
        /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             colorOn = resources.getColor(R.color.colorAccent, context.theme)
             colorBorder = colorOn
         } else {
             colorOn = resources.getColor(R.color.colorAccent)
             colorBorder = colorOn
         }*/
        paint = Paint()
        paint!!.isAntiAlias = true
        leftBgArc = RectF()
        rightBgArc = RectF()
        leftFgArc = RectF()
        rightFgArc = RectF()
        thumbBounds = RectF()
        colorOff = Color.parseColor("#FFFFFF")
        colorTextOff = Color.parseColor("#FFFFFF")
        colorTextOn = Color.parseColor("#FFFFFF")
        colorDisabled = Color.parseColor("#D3D3D3")
    }

    private fun initProperties(attrs: AttributeSet) {
        val tarr: TypedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.Toggle, 0, 0)
        val N = tarr.indexCount
        for (i in 0 until N) {
            when (tarr.getIndex(i)) {
                R.styleable.Toggle_on -> {
                    isOn = tarr.getBoolean(R.styleable.Toggle_on, false)
                }

                R.styleable.Toggle_colorOff -> {
                    colorOff =
                        tarr.getColor(R.styleable.Toggle_colorOff, Color.parseColor("#FFFFFF"))
                }

                R.styleable.Toggle_colorBorder -> {
                    val accentColor: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        resources.getColor(R.color.colorAccent, context.theme)
                    } else {
                        resources.getColor(R.color.colorAccent)
                    }
                    colorBorder = tarr.getColor(R.styleable.Toggle_colorBorder, accentColor)
                }

                R.styleable.Toggle_colorOn -> {
                    val accentColor: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        resources.getColor(R.color.colorAccent, context.theme)
                    } else {
                        resources.getColor(R.color.colorAccent)
                    }
                    colorOn = tarr.getColor(R.styleable.Toggle_colorOn, accentColor)
                }

                R.styleable.Toggle_colorDisabled -> {
                    colorDisabled =
                        tarr.getColor(R.styleable.Toggle_colorOff, Color.parseColor("#D3D3D3"))
                }

                R.styleable.Toggle_textOff -> {
                    labelOff = tarr.getString(R.styleable.Toggle_textOff)
                }

                R.styleable.Toggle_textOn -> {
                    labelOn = tarr.getString(R.styleable.Toggle_textOn)
                }

                R.styleable.Toggle_android_textSize -> {
                    val defaultTextSize: Int =
                        (12f * resources.displayMetrics.scaledDensity).toInt()
                    textSize =
                        tarr.getDimensionPixelSize(
                            R.styleable.Toggle_android_textSize,
                            defaultTextSize
                        )
                }

                R.styleable.Toggle_android_enabled -> {
                    enabled = tarr.getBoolean(R.styleable.Toggle_android_enabled, false)
                }

                R.styleable.Toggle_colorTextOff -> {
                    colorTextOff =
                        tarr.getColor(R.styleable.Toggle_colorTextOff, Color.parseColor("#FFFFFF"))
                }

                R.styleable.Toggle_colorTextOn -> {
                    colorTextOn =
                        tarr.getColor(R.styleable.Toggle_colorTextOn, Color.parseColor("#FFFFFF"))
                }
            }
        }
    }

    protected override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint!!.textSize = textSize.toFloat()

//      Drawing Switch background here
        run {
            paint!!.color = colorBorder
            canvas.drawArc(leftBgArc!!, 90f, 180f, false, paint!!)
            canvas.drawArc(rightBgArc!!, 90f, -180f, false, paint!!)
            canvas.drawRect(
                outerRadii.toFloat(),
                0f,
                (width - outerRadii).toFloat(),
                height.toFloat(),
                paint!!
            )
            paint!!.color = colorOff
            canvas.drawArc(leftFgArc!!, 90f, 180f, false, paint!!)
            canvas.drawArc(rightFgArc!!, 90f, -180f, false, paint!!)
            canvas.drawRect(
                outerRadii.toFloat(),
                (padding / 10).toFloat(),
                (width - outerRadii).toFloat(),
                (height - padding / 10).toFloat(),
                paint!!
            )
        }

//      Drawing Switch Labels here
        val MAX_CHAR = "N"
        val textCenter = paint!!.measureText(MAX_CHAR) / 2
        if (isOn) {
            var alpha: Int =
                (((width ushr 1) - thumbBounds!!.centerX()) / ((width ushr 1) - thumbOffCenterX) * 255).toInt()
            alpha = if (alpha < 0) 0 else if (alpha > 255) 255 else alpha
            val onColor =
                Color.argb(
                    alpha,
                    Color.red(colorTextOn),
                    Color.green(colorTextOn),
                    Color.blue(colorTextOn)
                )
            paint!!.color = onColor
            val centerX: Float
            alpha =
                ((thumbBounds!!.centerX() - (width ushr 1)) / (thumbOnCenterX - (width ushr 1)) * 255).toInt()
            alpha = if (alpha < 0) 0 else if (alpha > 255) 255 else alpha
            val offColor =
                Color.argb(
                    alpha,
                    Color.red(colorTextOff),
                    Color.green(colorTextOff),
                    Color.blue(colorTextOff)
                )
            paint!!.color = offColor
            val maxSize: Int = width - (padding shl 1) - (thumbRadii shl 1)
            centerX = ((padding ushr 1) + maxSize - padding ushr 1).toFloat()
            canvas.drawText(
                labelOn!!,
                padding + centerX - paint!!.measureText(labelOn) / 2,
                (height ushr 1) + textCenter,
                paint!!
            )
        } else {
            var alpha: Int =
                ((thumbBounds!!.centerX() - (width ushr 1)) / (thumbOnCenterX - (width ushr 1)) * 255).toInt()
            alpha = if (alpha < 0) 0 else if (alpha > 255) 255 else alpha
            val offColor =
                Color.argb(
                    alpha,
                    Color.red(colorTextOn),
                    Color.green(colorTextOn),
                    Color.blue(colorTextOn)
                )
            paint!!.color = offColor
            alpha =
                (((width ushr 1) - thumbBounds!!.centerX()) / ((width ushr 1) - thumbOffCenterX) * 255).toInt()
            alpha = if (alpha < 0) 0 else if (alpha > 255) 255 else alpha
            val onColor: Int = if (isEnabled) {
                Color.argb(
                    alpha,
                    Color.red(colorTextOn),
                    Color.green(colorTextOn),
                    Color.blue(colorTextOn)
                )
            } else {
                Color.argb(
                    alpha,
                    Color.red(colorDisabled),
                    Color.green(colorDisabled),
                    Color.blue(colorDisabled)
                )
            }
            paint!!.color = onColor
            val centerX: Float =
                (width - padding - (padding + (padding ushr 1) + (thumbRadii shl 1)) ushr 1).toFloat()
            canvas.drawText(
                labelOff!!,
                padding + (padding ushr 1) + (thumbRadii shl 1) + centerX - paint!!.measureText(
                    labelOff
                ) / 2,
                (height ushr 1) + textCenter,
                paint!!
            )
        }

//      Drawing Switch Thumb here
        run {
            var alpha =
                ((thumbBounds!!.centerX() - thumbOffCenterX) / (thumbOnCenterX - thumbOffCenterX) * 255).toInt()
            alpha = if (alpha < 0) 0 else if (alpha > 255) 255 else alpha
            val offColor =
                Color.argb(
                    alpha,
                    Color.red(colorBorder),
                    Color.green(colorBorder),
                    Color.blue(colorBorder)
                )
            paint!!.color = offColor
            canvas.drawCircle(
                thumbBounds!!.centerX(),
                thumbBounds!!.centerY(),
                thumbRadii.toFloat(),
                paint!!
            )
            alpha =
                ((thumbOnCenterX - thumbBounds!!.centerX()) / (thumbOnCenterX - thumbOffCenterX) * 255).toInt()
            alpha = if (alpha < 0) 0 else if (alpha > 255) 255 else alpha
            val onColor: Int = if (isEnabled) {
                Color.argb(
                    alpha,
                    Color.red(colorBorder),
                    Color.green(colorBorder),
                    Color.blue(colorBorder)
                )
            } else {
                Color.argb(
                    alpha,
                    Color.red(colorDisabled),
                    Color.green(colorDisabled),
                    Color.blue(colorDisabled)
                )
            }
            paint!!.color = onColor
            canvas.drawCircle(
                thumbBounds!!.centerX(),
                thumbBounds!!.centerY(),
                thumbRadii.toFloat(),
                paint!!
            )
        }
    }

    protected override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth: Int = resources.getDimensionPixelSize(R.dimen.labeled_default_width)
        val desiredHeight: Int =
            resources.getDimensionPixelSize(R.dimen.labeled_default_height)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        width = if (widthMode == MeasureSpec.EXACTLY) {
            widthSize
        } else if (widthMode == MeasureSpec.AT_MOST) {
            desiredWidth.coerceAtMost(widthSize)
        } else {
            desiredWidth
        }
        height = when (heightMode) {
            MeasureSpec.EXACTLY -> {
                heightSize
            }

            MeasureSpec.AT_MOST -> {
                desiredHeight.coerceAtMost(heightSize)
            }

            else -> {
                desiredHeight
            }
        }
        setMeasuredDimension(width, height)
        outerRadii = width.coerceAtMost(height) ushr 1
        thumbRadii = (width.coerceAtMost(height) / 2.88f).toInt()
        padding = height - thumbRadii ushr 1
        thumbBounds!![(width - padding - thumbRadii).toFloat(), padding.toFloat(), (width - padding).toFloat()] =
            (height - padding).toFloat()
        thumbOnCenterX = thumbBounds!!.centerX()
        thumbBounds!![padding.toFloat(), padding.toFloat(), (padding + thumbRadii).toFloat()] =
            (height - padding).toFloat()
        thumbOffCenterX = thumbBounds!!.centerX()
        if (isOn) {
            thumbBounds!![(width - padding - thumbRadii).toFloat(), padding.toFloat(), (width - padding).toFloat()] =
                (height - padding).toFloat()
        } else {
            thumbBounds!![padding.toFloat(), padding.toFloat(), (padding + thumbRadii).toFloat()] =
                (height - padding).toFloat()
        }
        leftBgArc!![0f, 0f, (outerRadii shl 1).toFloat()] = height.toFloat()
        rightBgArc!![(width - (outerRadii shl 1)).toFloat(), 0f, width.toFloat()] = height.toFloat()
        leftFgArc!![(padding / 10).toFloat(), (padding / 10).toFloat(), ((outerRadii shl 1) - padding / 10).toFloat()] =
            (height - padding / 10).toFloat()
        rightFgArc!![(width - (outerRadii shl 1) + padding / 10).toFloat(), (padding / 10).toFloat(), (width - padding / 10).toFloat()] =
            (height - padding / 10).toFloat()
    }

    /**
     * Call this view's OnClickListener, if it is defined.  Performs all normal
     * actions associated with clicking: reporting accessibility event, playing
     * a sound, etc.
     *
     * @return True there was an assigned OnClickListener that was called, false
     * otherwise is returned.
     */
    override fun performClick(): Boolean {
        super.performClick()
        if (isOn) {
            val switchColor =
                ValueAnimator.ofFloat((width - padding - thumbRadii).toFloat(), padding.toFloat())
            switchColor.addUpdateListener { animation: ValueAnimator ->
                val value = animation.animatedValue as Float
                thumbBounds!![value, thumbBounds!!.top, value + thumbRadii] = thumbBounds!!.bottom
                invalidate()
            }
            switchColor.interpolator = AccelerateDecelerateInterpolator()
            switchColor.duration = 250
            switchColor.start()
        } else {
            val switchColor =
                ValueAnimator.ofFloat(padding.toFloat(), (width - padding - thumbRadii).toFloat())
            switchColor.addUpdateListener { animation: ValueAnimator ->
                val value = animation.animatedValue as Float
                thumbBounds!![value, thumbBounds!!.top, value + thumbRadii] = thumbBounds!!.bottom
                invalidate()
            }
            switchColor.interpolator = AccelerateDecelerateInterpolator()
            switchColor.duration = 250
            switchColor.start()
        }
        isOn = !isOn
        if (onToggledListener != null) {
            onToggledListener.onSwitched(this, isOn)
        }
        return true
    }

    /**
     * Method to handle touch screen motion events.
     *
     * @param event The motion event.
     * @return True if the event was handled, false otherwise.
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (isEnabled) {
            val x = event.x
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startTime = System.currentTimeMillis()
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    if (x - (thumbRadii ushr 1) > padding && x + (thumbRadii ushr 1) < width - padding) {
                        thumbBounds!![x - (thumbRadii ushr 1), thumbBounds!!.top, x + (thumbRadii ushr 1)] =
                            thumbBounds!!.bottom
                        invalidate()
                    }
                    true
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    val endTime = System.currentTimeMillis()
                    val span = endTime - startTime
                    if (span < 200) {
                        performClick()
                    } else {
                        if (x >= width ushr 1) {
                            val switchColor = ValueAnimator.ofFloat(
                                (if (x > width - padding - thumbRadii) width - padding - thumbRadii else x).toFloat(),
                                (width - padding - thumbRadii).toFloat()
                            )
                            switchColor.addUpdateListener { animation: ValueAnimator ->
                                val value = animation.animatedValue
                                thumbBounds!![value as Float, thumbBounds!!.top, value + thumbRadii] =
                                    thumbBounds!!.bottom
                                invalidate()
                            }
                            switchColor.interpolator = AccelerateDecelerateInterpolator()
                            switchColor.duration = 250
                            switchColor.start()
                            isOn = true
                        } else {
                            val switchColor = ValueAnimator.ofFloat(
                                (if (x < padding) padding else x).toFloat(),
                                padding.toFloat()
                            )
                            switchColor.addUpdateListener { animation: ValueAnimator ->
                                val value = animation.animatedValue as Float
                                thumbBounds!![value, thumbBounds!!.top, value + thumbRadii] =
                                    thumbBounds!!.bottom
                                invalidate()
                            }
                            switchColor.interpolator = AccelerateDecelerateInterpolator()
                            switchColor.duration = 250
                            switchColor.start()
                            isOn = false
                        }
                        if (onToggledListener != null) {
                            onToggledListener.onSwitched(this, isOn)
                        }
                    }
                    invalidate()
                    true
                }

                else -> {
                    super.onTouchEvent(event)
                }
            }
        } else {
            false
        }
    }

    /**
     *
     * Returns the color value for colorOn.
     *
     * @return color value for label and thumb in off state and background in on state.
     */
    fun getColorOn(): Int {
        return colorOn
    }

    /**
     *
     * Changes the on color value of this Switch.
     *
     * @param colorOn color value for label and thumb in off state and background in on state.
     */
    fun setColorOn(colorOn: Int) {
        this.colorOn = colorOn
        invalidate()
    }

    /**
     *
     * Returns the color value for colorOff.
     *
     * @return color value for label and thumb in on state and background in off state.
     */
    fun getColorOff(): Int {
        return colorOff
    }

    /**
     *
     * Changes the off color value of this Switch.
     *
     * @param colorOff color value for label and thumb in on state and background in off state.
     */
    fun setColorOff(colorOff: Int) {
        this.colorOff = colorOff
        invalidate()
    }

    /**
     *
     * Returns text label when switch is in on state.
     *
     * @return text label when switch is in on state.
     */
    fun getLabelOn(): String? {
        return labelOn
    }

    /**
     *
     * Changes text label when switch is in on state.
     *
     * @param labelOn text label when switch is in on state.
     */
    fun setLabelOn(labelOn: String?) {
        this.labelOn = labelOn
        invalidate()
    }

    /**
     *
     * Returns text label when switch is in off state.
     *
     * @return text label when switch is in off state.
     */
    fun getLabelOff(): String? {
        return labelOff
    }

    /**
     *
     * Changes text label when switch is in off state.
     *
     * @param labelOff text label when switch is in off state.
     */
    fun setLabelOff(labelOff: String?) {
        this.labelOff = labelOff
        invalidate()
    }

    /**
     *
     * Changes the boolean state of this Switch.
     *
     * @param on true to turn switch on, false to turn it off.
     */
    override fun setOn(on: Boolean) {
        super.setOn(on)
        if (isOn) {
            thumbBounds!![(width - padding - thumbRadii).toFloat(), padding.toFloat(), (width - padding).toFloat()] =
                (height - padding).toFloat()
        } else {
            thumbBounds!![padding.toFloat(), padding.toFloat(), (padding + thumbRadii).toFloat()] =
                (height - padding).toFloat()
        }
        invalidate()
    }

    /**
     *
     * Returns the color value for Switch disabled state.
     *
     * @return color value used by background, border and thumb when switch is disabled.
     */
    fun getColorDisabled(): Int {
        return colorDisabled
    }

    /**
     *
     * Changes the color value for Switch disabled state.
     *
     * @param colorDisabled color value used by background, border and thumb when switch is disabled.
     */
    fun setColorDisabled(colorDisabled: Int) {
        this.colorDisabled = colorDisabled
        invalidate()
    }

    /**
     *
     * Returns the color value for Switch border.
     *
     * @return color value used by Switch border.
     */
    fun getColorBorder(): Int {
        return colorBorder
    }

    /**
     *
     * Changes the color value for Switch disabled state.
     *
     * @param colorBorder color value used by Switch border.
     */
    fun setColorBorder(colorBorder: Int) {
        this.colorBorder = colorBorder
        invalidate()
    }

    /**
     *
     * Returns the text size for Switch on/off label.
     *
     * @return text size for Switch on/off label.
     */
    fun getTextSize(): Int {
        return textSize
    }

    /**
     *
     * Changes the text size for Switch on/off label.
     *
     * @param textSize text size for Switch on/off label.
     */
    fun setTextSize(textSize: Int) {
        this.textSize = (textSize * resources.displayMetrics.scaledDensity).toInt()
        invalidate()
    }
}