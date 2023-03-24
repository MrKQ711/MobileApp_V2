package com.thai.salesapp

import android.content.Intent
import androidx.fragment.app.DialogFragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QtyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QtyFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_qty, container, false)

        var et = v.findViewById<EditText>(R.id.et_qty)
        var btn = v.findViewById<Button>(R.id.btn_qty)

        btn.setOnClickListener {

            var url = "http://10.20.22.114/SalesWeb/add_temp.php?mobile="+UserInfo.mobile+"&itemid="+UserInfo.itemId+
                    "&qty="+et.text.toString()
            var rq:RequestQueue=Volley.newRequestQueue(activity)
            var sr=StringRequest(Request.Method.GET,url, { response ->

                var i = Intent(activity, OrderAct::class.java)
                startActivity(i)

            }, { _ ->
                Toast.makeText(activity, "Failed!", Toast.LENGTH_LONG).show()
            })

            rq.add(sr)
        }

        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QtyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QtyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}