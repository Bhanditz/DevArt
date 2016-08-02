package com.hujiang.alg.sample

import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.hujiang.devart.security.AlgorithmUtils
import kotlin.concurrent.thread

/**
 * Created by rarnu on 7/25/16.
 */
class BASE64Activity: BaseActivity(), View.OnClickListener {

    private var etEncSrc: EditText? = null
    private var tvEncDest: TextView? = null
    private var btnEncGo: Button? = null
    private var etDecSrc: EditText? = null
    private var tvDecDest: TextView? = null
    private var btnDecGo: Button? = null

    override fun getLayoutId(): Int = R.layout.activity_base64

    override fun init() {
        etEncSrc = findViewById(R.id.etEncSrc) as EditText?
        tvEncDest = findViewById(R.id.tvEncDest) as TextView?
        btnEncGo = findViewById(R.id.btnEncGo) as Button?
        etDecSrc = findViewById(R.id.etDecSrc) as EditText?
        tvDecDest = findViewById(R.id.tvDecDest) as TextView?
        btnDecGo = findViewById(R.id.btnDecGo) as Button?

        btnEncGo?.setOnClickListener(this)
        btnDecGo?.setOnClickListener(this)
    }

    private val h = object: Handler() {
        override fun handleMessage(msg: Message?) {
            if (msg!!.what == 100) {
                btnEncGo?.isEnabled = true
                val enc = msg.obj as String
                tvEncDest?.text = enc
                etDecSrc?.setText(enc)
            } else if (msg.what == 101) {
                btnDecGo?.isEnabled = true
                val dec = msg.obj as String
                tvDecDest?.text = dec
            }
            super.handleMessage(msg)
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnEncGo -> {
                btnEncGo?.isEnabled = false
                val ori = etEncSrc?.text.toString()
                thread {
                    val enc = AlgorithmUtils.base64EncryptString(ori)
                    val m = Message()
                    m.what = 100
                    m.obj = enc
                    h.sendMessage(m)
                }
            }
            R.id.btnDecGo -> {
                btnDecGo?.isEnabled = false
                val ori = etDecSrc?.text.toString()
                thread {
                    val dec = AlgorithmUtils.base64DecryptString(ori)
                    val m = Message()
                    m.what = 101
                    m.obj = dec
                    h.sendMessage(m)
                }
            }
        }
    }

}