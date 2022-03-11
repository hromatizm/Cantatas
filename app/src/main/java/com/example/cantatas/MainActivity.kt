package com.example.cantatas

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.InputStream

class MainActivity : AppCompatActivity(), RecyclerCantataAdapter.OnCantataClickListener {

    companion object {
        // Ключи для передачи данных между активностями
        const val CANTATA = "CANTATA"
        const val RATING_REQUEST = 777

        // Ключ для сохранения состояния:
        const val SAVE_STATE = "SAVE_STATE"
    }

    lateinit var list: RecyclerView
    lateinit var adapter: RecyclerCantataAdapter
    var cantatas = ArrayList<Cantata>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list = findViewById(R.id.list)

        // Парсим данные из файла
        val inputStream: InputStream = resources.openRawResource(R.raw.cantatas)
        val lineList = mutableListOf<String>()

        inputStream.bufferedReader().forEachLine { lineList.add(it) }

        for ((counter, line) in lineList.withIndex()) {
            // bwv
            val bwvNum = line.substringBefore(" ")
            val bwv = "BWV $bwvNum"

            // name
            val lineForName = line.substringAfter("$bwvNum ")
            val name = if (lineForName.contains(" ( "))
                lineForName.substringBefore(" ( ")
            else if (lineForName.contains("Occasion: "))
                lineForName.substringBefore("Occasion: ")
            else
                lineForName

            // date
            val lineForDate = if (lineForName.contains(" ( "))
                lineForName.substringAfter("$name ( ")
            else
                "?"
            val date = "Date: ${lineForDate.substringBefore(" )")}"

            // occasion
            val lineForOccasion = if (lineForName.contains("Occasion: "))
                lineForName.substringAfter("Occasion: ")
            else
                "?"
            val occasion = "Occasion: ${lineForOccasion.substringBefore(" Text by: ")}"

            //  textBy
            val textBy = if (lineForName.contains("Text by: "))
                "Text by: ${lineForName.substringAfter("Text by: ")}"
            else
                ""
            val cantata = Cantata(bwv, name, date, occasion, textBy, counter)
            println(cantata.bwv)
            println(cantata.cleanDate)
            cantatas.add(cantata)
        }
        savedInstanceState?.run {
            val savedState = getSerializable(SAVE_STATE)
            cantatas = savedState as ArrayList<Cantata>
        }
        adapter = RecyclerCantataAdapter(cantatas)
        val layoutManager = LinearLayoutManager(this)
        adapter.setOnCantataClickListener(this)

        list.layoutManager = layoutManager
        val decoration = DividerItemDecoration(this, layoutManager.orientation)

        list.addItemDecoration(decoration)
        list.adapter = adapter
    }

    override fun onCantataClick(cantata: Cantata) {
        println("test")
        val intent = Intent(this, CantataCard::class.java)
        intent.putExtra(CANTATA, cantata)

        startActivityForResult(intent, RATING_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RATING_REQUEST)
            data?.run {
                val cantata = getSerializableExtra(CANTATA) as Cantata
                val changedCantata = cantatas.first { it.id == cantata.id }
                changedCantata.rating = cantata.rating
                adapter.notifyDataSetChanged()
            } else
            super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_up -> {
                cantatas.sortWith(Cantata.activeComparator)
                adapter.notifyDataSetChanged()
                return true
            }
            R.id.menu_down -> {
                cantatas.sortWith(Cantata.activeComparator.reversed())
                adapter.notifyDataSetChanged()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun chooseSortStyle(item: MenuItem) {
        when (item.itemId) {
            R.id.by_bwv -> {
            }
            R.id.by_date -> {
                Cantata.activeComparator = Cantata.byDate
            }
            R.id.by_name -> {
                Cantata.activeComparator = Cantata.byName
            }
            R.id.by_rating -> {
                Cantata.activeComparator = Cantata.byRating
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(SAVE_STATE, cantatas)
        super.onSaveInstanceState(outState)

    }
}