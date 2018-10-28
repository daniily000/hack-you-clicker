package com.daniily000.android.hackyou.gamedata

import android.content.Context
import com.daniily000.android.hackyou.R
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.util.*


class ChapterManager(private val context: Context) {

    private val levelsJson: JSONObject
    val chapters: Array<Chapter>

    init {
        val m = context.packageManager
        val s = context.packageName
        val p = m.getPackageInfo(s, 0)


        levelsJson = JSONObject(getInputStreamAsString(context.resources.openRawResource(R.raw.levels)))
        chapters = parseChaptersFromJsonArray(levelsJson.getJSONArray("chapters"))
    }

    private fun getInputStreamAsString(io: InputStream): String {
        return String(io.readBytes())
    }

    private fun parseChaptersFromJsonArray(chapters: JSONArray): Array<Chapter> {

        val chapterList = ArrayList<Chapter>()

        var i = -1;
        while (++i < chapters.length()) {
            val jsonChapter = chapters.getJSONObject(i)
            chapterList.add(Chapter(
                    context.getString(R.string::class.java.getDeclaredField(jsonChapter.getString("chapterNameResName")).get(0) as Int),
                    parseLevelsFromJsonArray(jsonChapter.getJSONArray("levels"))))
        }
        return chapterList.toTypedArray()
    }

    private fun parseLevelsFromJsonArray(levels: JSONArray): Array<Level> {

        val levelList = ArrayList<Level>()

        var i = -1;
        while (++i < levels.length()) {
            val level = levels.getJSONObject(i)
            val levelBuilder = Level.Builder(level.getInt("time"), level.getInt("points"))
            val levelModel = level.getJSONArray("levelModel")

            var ii = -1
            while (++ii < levelModel.length()) {
                levelBuilder.addObject(levelModel.getString(ii))
            }
            levelList.add(levelBuilder.build())
        }
        return levelList.toTypedArray()
    }

    companion object {
        private const val TAG = "ChapterManager"
    }


}