package com.github.ljarka.bakingapp.network.model

import android.os.Parcel
import android.os.Parcelable

data class Recipe(val id: Int, val ingredients: List<IngredientData>, val steps: List<Step>, val name: String, val servings: Int,
        val image: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.createTypedArrayList(IngredientData),
            parcel.createTypedArrayList(Step),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeTypedList(ingredients)
        parcel.writeTypedList(steps)
        parcel.writeString(name)
        parcel.writeInt(servings)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Recipe> {
        override fun createFromParcel(parcel: Parcel): Recipe {
            return Recipe(parcel)
        }

        override fun newArray(size: Int): Array<Recipe?> {
            return arrayOfNulls(size)
        }
    }
}

data class IngredientData(val quantity: Float, val measure: String, val ingredient: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readFloat(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(quantity)
        parcel.writeString(measure)
        parcel.writeString(ingredient)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IngredientData> {
        override fun createFromParcel(parcel: Parcel): IngredientData {
            return IngredientData(parcel)
        }

        override fun newArray(size: Int): Array<IngredientData?> {
            return arrayOfNulls(size)
        }
    }
}

data class Step(val id: Int, val shortDescription: String, val description: String, val videoURL: String,
        val thumbnailURL: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(shortDescription)
        parcel.writeString(description)
        parcel.writeString(videoURL)
        parcel.writeString(thumbnailURL)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Step> {
        override fun createFromParcel(parcel: Parcel): Step {
            return Step(parcel)
        }

        override fun newArray(size: Int): Array<Step?> {
            return arrayOfNulls(size)
        }
    }
}