package com.example.csproject

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MemberDatabase (private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("MemberPreferences", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun addMember(m: Member) {
        val members = getMembers().toMutableList()
        members.add(m)
        saveMembers(members)
    }

    fun checkIfMember(m:Member): Int {
        val members = getMembers().toMutableList()
        val iterator = members.iterator()
        while (iterator.hasNext()) {
            val currentItem = iterator.next()
            if (currentItem.username == m.username && currentItem.password == m.password) {
                return 1
            }
            else if (currentItem.username == m.username) {
                return -1
            }
        }
        return 0
    }
    fun getMembers(): List<Member> {
        val json = sharedPreferences.getString("memberItem", null)
        return if (json != null) {
            gson.fromJson(json, object : TypeToken<List<Member>>() {}.type)
        } else {
            emptyList()
        }
    }

    fun isLoggedIn(): Boolean {

        val members = getMembers().toMutableList()
        val iterator = members.iterator()
        while (iterator.hasNext()) {
            val currentItem = iterator.next()
            if (currentItem.signedIn) {
                return true
            }
        }
        return false
    }

    fun returnLoggedIn(): String {
        val members = getMembers().toMutableList()
        val iterator = members.iterator()
        while (iterator.hasNext()) {
            val currentItem = iterator.next()
            if(currentItem.signedIn) {
                return currentItem.username
            }
        }
        return ""
    }

    fun logout(name: String) {
        val members = getMembers().toMutableList()
        val iterator = members.iterator()
        while (iterator.hasNext()) {
            val currentItem = iterator.next()
            if (currentItem.username == name) {
                currentItem.signedIn = false

            }
        }
        saveMembers(members)
    }

    fun login(name: String) {
        val members = getMembers().toMutableList()
        val iterator = members.iterator()
        while (iterator.hasNext()) {
            val currentItem = iterator.next()
            if (currentItem.username == name) {
                currentItem.signedIn = true

            }
        }
        saveMembers(members)
    }
    fun removeAllMembers() {
        saveMembers(emptyList())
    }

    private fun saveMembers(items: List<Member>) {
        val json = gson.toJson(items)
        sharedPreferences.edit().putString("memberItem", json).apply()
    }
}