package com.github.ljarka.bakingapp.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class IngredientData implements Parcelable {
    private float quantity;
    private String measure;
    private String ingredient;

    protected IngredientData(Parcel in) {
        quantity = in.readFloat();
        measure = in.readString();
        ingredient = in.readString();
    }

    public static final Creator<IngredientData> CREATOR = new Creator<IngredientData>() {
        @Override
        public IngredientData createFromParcel(Parcel in) {
            return new IngredientData(in);
        }

        @Override
        public IngredientData[] newArray(int size) {
            return new IngredientData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingredient);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IngredientData that = (IngredientData) o;
        return Float.compare(that.quantity, quantity) == 0 && Objects.equals(measure, that.measure) && Objects.equals(ingredient,
                that.ingredient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, measure, ingredient);
    }

    public float getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }
}
