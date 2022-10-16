package com.todayeouido.tayapp.presentation.navigation


import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson


class GroupBillPostType : NavType<GroupBillParcelableModel>(isNullableAllowed = false){

    override fun get(bundle: Bundle, key: String): GroupBillParcelableModel? {
        return bundle.getParcelable(key)
    }
    override fun parseValue(value: String): GroupBillParcelableModel {
        return Gson().fromJson(value, GroupBillParcelableModel::class.java)
    }
    override fun put(bundle: Bundle, key: String, value: GroupBillParcelableModel) {
        bundle.putParcelable(key, value)
    }
}