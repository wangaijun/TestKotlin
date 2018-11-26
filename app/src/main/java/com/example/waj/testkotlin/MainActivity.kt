package com.example.waj.testkotlin

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

/**类型安全的构建模式*/
inline fun <reified TV : View> Context.v(init: TV.() -> Unit): TV{
    //获得构造器
    val cns = TV::class.java.getConstructor(Context::class.java)
    //创建TV对象的实例
    return cns.newInstance(this)
}

inline fun <reified V:View> ViewGroup.v(init:V.()->Unit):V{
    val cns = V::class.java.getConstructor(Context::class.java)
    val view = cns.newInstance(context)
    addView(view)
    view.init()
    return view
}

/**扩展函数和属性*/
fun View.dp_f(dp:Float):Float{
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,dp,context.resources.displayMetrics
    )
}
fun View.dp_i(dp:Float):Int{
    return dp_f(dp).toInt()
}

var View.padLeft:Int
    set(value) {
        setPadding(value,paddingTop,paddingRight,paddingBottom)
    }
    get() {return paddingLeft}

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv.text = tv.dp_i(100f).toString()
        tv.padLeft = -10

        val view = v<LinearLayout>{
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
            orientation = LinearLayout.VERTICAL
        }

    }
}
