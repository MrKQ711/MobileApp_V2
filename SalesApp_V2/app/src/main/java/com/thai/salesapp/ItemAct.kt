package com.thai.salesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_item.*

class ItemAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        var cat: String? =intent.getStringExtra("cat")
        var url = "http://10.20.22.114/SalesWeb/get_items.php?category="+cat
        var list = ArrayList<Item>()

        var rq:RequestQueue=Volley.newRequestQueue(this)
        var jar = JsonArrayRequest(Request.Method.GET,url,null, { response ->

            for (x in 0..response.length()-1)
                list.add(Item(response.getJSONObject(x).getInt("id"),response.getJSONObject(x).getString("name"),
                    response.getJSONObject(x).getDouble("price"),response.getJSONObject(x).getString("photo")))

            var adp = ItemAdapter(this,list)
            item_rv.layoutManager=LinearLayoutManager(this)
            item_rv.adapter=adp

        }, {
            Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show()
        })

        rq.add(jar)
    }
}