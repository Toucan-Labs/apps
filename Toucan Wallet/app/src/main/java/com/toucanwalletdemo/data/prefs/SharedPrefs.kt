package com.toucanwalletdemo.data.prefs

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.toucanwalletdemo.ToucanPayApp
import com.toucanwalletdemo.data.models.Reward
import kotlin.reflect.KProperty

abstract class SharedPrefs(private val sharedPrefsName: String) {

    protected val sharedPrefs: SharedPreferences by lazy {
        ToucanPayApp.instance.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE)
    }

    fun clear() {
        sharedPrefs.edit().clear().apply()
    }
}

fun SharedPreferences.boolean(key: String, defValue: Boolean) =
    SharedPrefsDelegate(
        this,
        key,
        defValue,
        SharedPreferences::getBoolean,
        SharedPreferences.Editor::putBoolean
    )

fun SharedPreferences.long(key: String, defValue: Long) =
    SharedPrefsDelegate(
        this,
        key,
        defValue,
        SharedPreferences::getLong,
        SharedPreferences.Editor::putLong
    )

fun SharedPreferences.int(key: String, defValue: Int) =
    SharedPrefsDelegate(
        this,
        key,
        defValue,
        SharedPreferences::getInt,
        SharedPreferences.Editor::putInt
    )

fun SharedPreferences.string(key: String, defValue: String?) =
    SharedPrefsDelegate(
        this,
        key,
        defValue,
        SharedPreferences::getString,
        SharedPreferences.Editor::putString
    )

fun Reward.serializeReward(): String = Gson().toJson(this)

fun String.deserializeReward(): Reward = Gson().fromJson(this, object: TypeToken<Reward>() {}.type)

open class SharedPrefsDelegate<T>(
    private val prefs: SharedPreferences,
    private val key: String,
    private val defValue: T,
    private val getFunction: (SharedPreferences, String, T) -> T,
    private val setFunction: (SharedPreferences.Editor, String, T) -> SharedPreferences.Editor
) {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getFunction(prefs, key, defValue)
    }

    @SuppressLint("CommitPrefEdits")
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        setFunction(prefs.edit(), key, value).apply()
    }
}