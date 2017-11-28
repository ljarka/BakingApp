package com.github.ljarka.bakingapp.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Objects;

public class Recipe implements Parcelable {
    private int id;
    private List<IngredientData> ingredients;
    private List<Step> steps;
    private String name;
    private int servings;
    private String image;

    protected Recipe(Parcel in) {
        id = in.readInt();
        ingredients = in.createTypedArrayList(IngredientData.CREATOR);
        steps = in.createTypedArrayList(Step.CREATOR);
        name = in.readString();
        servings = in.readInt();
        image = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Recipe recipe = (Recipe) o;
        return id == recipe.id
                && servings == recipe.servings
                && Objects.equals(ingredients, recipe.ingredients)
                && Objects.equals(steps, recipe.steps)
                && Objects.equals(name, recipe.name)
                && Objects.equals(image, recipe.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ingredients, steps, name, servings, image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeTypedList(ingredients);
        parcel.writeTypedList(steps);
        parcel.writeString(name);
        parcel.writeInt(servings);
        parcel.writeString(image);
    }

    public int getId() {
        return id;
    }

    public List<IngredientData> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public String getName() {
        return name;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }
}
