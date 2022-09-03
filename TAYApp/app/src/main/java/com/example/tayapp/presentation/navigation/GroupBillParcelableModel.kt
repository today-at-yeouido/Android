package com.example.tayapp.presentation.navigation

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.example.tayapp.data.remote.dto.scrap.ScrapBillItemDto
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
data class GroupBillParcelableModel(
    val BillList: @RawValue List<ScrapBillItemDto>
): Parcelable {
    companion object NavigationType : NavType<GroupBillParcelableModel>(isNullableAllowed = false) {
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
}
