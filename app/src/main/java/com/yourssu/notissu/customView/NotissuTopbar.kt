package com.yourssu.notissu.customView

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.yourssu.notissu.R
import kotlinx.android.synthetic.main.notissu_top_bar.view.*

class NotissuTopbar: ConstraintLayout {
    private var topbarContext: Context = context
    private var isBackButton: Boolean = false
    private var isPlusButton: Boolean = false
    private var isWebButton:Boolean = false

    constructor(context: Context) : super(context){
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context, attrs)
    }

    @SuppressLint("Recycle")
    private fun initView(context: Context, attrs: AttributeSet?) {
        topbarContext = context
        val inflater = LayoutInflater.from(topbarContext)
        val v = inflater.inflate(R.layout.notissu_top_bar, this, false)
        addView(v)


        if (attrs == null) {
            return
        }

        val array = topbarContext.obtainStyledAttributes(attrs, R.styleable.NotissuTopbar)

        isBackButton = array.getBoolean(R.styleable.NotissuTopbar_setBackButton, false)
        isPlusButton = array.getBoolean(R.styleable.NotissuTopbar_setPlusButton, false)
        isWebButton = array.getBoolean(R.styleable.NotissuTopbar_setWebButton, false)

        array.recycle()

        settingView()
    }

    private fun settingView() {
        backButton.visibility = if (isBackButton) View.VISIBLE else View.GONE
        plusButton.visibility = if (isPlusButton) View.VISIBLE else View.GONE
        webButton.visibility = if (isWebButton) View.VISIBLE else View.GONE
    }

    fun setTitle(title: String) {
        topBarTitle.text = title
    }

    fun setBackButtonClicked(click: () -> Unit) {
        backButton.setOnClickListener {
            click()
        }
    }

    fun setPlusButtonClicked(click: () -> Unit) {
        plusButton.setOnClickListener {
            click()
        }
    }

    fun setWebButtonClicked(click: () -> Unit) {
        webButton.setOnClickListener {
            click()
        }
    }
}