package com.example.pienakumusaraksts

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pienakumusaraksts.DTO.PienakumuSaraksts
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.rv_child_main.*

class MainActivity : AppCompatActivity() {

    lateinit var databaseHandler: DatabaseHandler
    var responsibilityId : Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        databaseHandler = DatabaseHandler(this)
        rv_main.layoutManager = LinearLayoutManager(this)
        responsibilityId = intent.getLongExtra(COL_ID, -1)
        
        fab_main.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.dialog_main, null)
            val name = view.findViewById<EditText>(R.id.tv_pienakumi)
            dialog.setView(view)
            dialog.setPositiveButton("Add") { _: DialogInterface, _: Int ->
                if (name.text.isNotEmpty()) {
                    val responsibility = PienakumuSaraksts()
                    responsibility.name = name.text.toString()
                    responsibility.id - responsibilityId
                    responsibility.isResolved = false
                    databaseHandler.addResponsibility(responsibility)
                    refreshList()
                }
            }
            dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

            }
            dialog.show()
        }
    }

    fun updateList(responsibility: PienakumuSaraksts) {
        val dialog = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_main, null)
        val name = view.findViewById<EditText>(R.id.tv_pienakumi)
        name.setText(responsibility.name)
        dialog.setView(view)
        dialog.setPositiveButton("Add") { _: DialogInterface, _: Int ->
            if (name.text.isNotEmpty()) {
                responsibility.name = name.text.toString()
                databaseHandler.updateResponsibilty(responsibility)
                refreshList()
            }
        }
        dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

        }
        dialog.show()
    }

    override fun onResume() {
        refreshList()
        super.onResume()
    }

    private fun refreshList() {
        rv_main.adapter = MainAdapter(this, databaseHandler.getResponsibility())
    }

    class MainAdapter(val activity: MainActivity, val list: MutableList<PienakumuSaraksts>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(activity).inflate(R.layout.rv_child_main, parent,false))
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.responsibility.text = list[position].name

            holder.menu.setOnClickListener {
                val popup = PopupMenu(activity,holder.menu)
                popup.inflate(R.menu.main_child)
                popup.setOnMenuItemClickListener {

                    when(it.itemId) {
                        R.id.menu_edit-> {
                            activity.updateList(list[position])
                        }
                        R.id.menu_delete-> {
                            activity.databaseHandler.deleteResponsibility(list[position].id)
                            activity.refreshList()
                        }
                        R.id.menu_mark_as_resolved-> {
                            activity.databaseHandler.updateAsResolved(list[position].id, true)
                            holder.resolved.visibility = View.VISIBLE
                        }
                        R.id.menu_reset-> {
                            activity.databaseHandler.updateAsResolved(list[position].id, false)
                            holder.resolved.visibility = View.INVISIBLE
                        }
                    }

                    true
                }
                popup.show()
            }
        }

        class ViewHolder(v : View) : RecyclerView.ViewHolder(v) {
            val responsibility : TextView = v.findViewById(R.id.tv_name)
            val menu : ImageView = v.findViewById(R.id.iv_menu)
            val resolved : ImageView = v.findViewById(R.id.iv_resolved)
        }
    }
}
