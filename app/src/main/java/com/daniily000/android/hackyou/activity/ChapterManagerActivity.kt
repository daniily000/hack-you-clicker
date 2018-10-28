package com.daniily000.android.hackyou.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Toast
import com.daniily000.android.hackyou.R
import com.daniily000.android.hackyou.gamedata.ChapterManager
import com.daniily000.android.hackyou.gamedata.Level
import kotlinx.android.synthetic.main.chapters_levels_activity.*

class ChapterManagerActivity : AppCompatActivity() {

    private var onLevels = false

    private val chapterNames = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chapters_levels_activity)

        val chapterManager = ChapterManager(this)

        for (i: Int in chapterManager.chapters.indices)
            chapterNames.add(
                    getString(R.string.chapter_label)
                            + (i + 1) + ": "
                            + chapterManager.chapters[i].chapterName)



        chapters_levels_list_view.adapter = ArrayAdapter<String>(
                this,
                R.layout.default_one_line_list_item,
                R.id.text1,
                chapterNames)


        chapters_levels_list_view.setOnItemClickListener { _, _, chapterPosition, _ ->

            onLevels = true
            val levelNames = ArrayList<String>()

            for (i: Int in chapterManager.chapters[chapterPosition].levels.indices)
                levelNames.add(getString(R.string.level_label) + (i + 1))

            chapters_levels_list_view.adapter = ArrayAdapter<String>(
                    this@ChapterManagerActivity,
                    R.layout.default_one_line_list_item,
                    levelNames)

            chapters_levels_list_view.setOnItemClickListener { levelParent, levelView, levelPosition, levelId ->
                startLevel(chapterManager.chapters[chapterPosition].levels[levelPosition])
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (onLevels) {
            chapters_levels_list_view.adapter = ArrayAdapter<String>(
                    this,
                    R.layout.default_one_line_list_item,
                    chapterNames)
            onLevels = false
        } else {
            finish()
        }
    }

    private fun startLevel(level: Level) {
        // TODO: Add creating a certain level support in GameActivity
        Toast.makeText(this, "Starting " + level, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val TAG = "ChapterManagerActivity"
    }
}