package com.example.plant_disease_detection;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Plants implements Parcelable {
    private int id;
    private String plantName;
    private String image;
    private String details;
    private String category;
    private String growingConditions;
    private String careTips;
    private String scientificName;
    private String difficultyLevel;
    private List<String> tags;
    private String source;
    private GrowingGuide growingGuide;

    public Plants(int id, String plantName, String image, String details, String category,
                  String growingConditions, String careTips, String scientificName,
                  String difficultyLevel, List<String> tags, String source, GrowingGuide growingGuide) {
        this.id = id;
        this.plantName = plantName;
        this.image = image;
        this.details = details;
        this.category = category;
        this.growingConditions = growingConditions;
        this.careTips = careTips;
        this.scientificName = scientificName;
        this.difficultyLevel = difficultyLevel;
        this.tags = tags;
        this.source = source;
        this.growingGuide = growingGuide;
    }

    protected Plants(Parcel in) {
        id = in.readInt();
        plantName = in.readString();
        image = in.readString();
        details = in.readString();
        category = in.readString();
        growingConditions = in.readString();
        careTips = in.readString();
        scientificName = in.readString();
        difficultyLevel = in.readString();
        tags = in.createStringArrayList();
        source = in.readString();
        growingGuide = in.readParcelable(GrowingGuide.class.getClassLoader());
    }

    public static final Creator<Plants> CREATOR = new Creator<Plants>() {
        @Override
        public Plants createFromParcel(Parcel in) {
            return new Plants(in);
        }

        @Override
        public Plants[] newArray(int size) {
            return new Plants[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getPlantName() {
        return plantName;
    }

    public String getImage() {
        return image;
    }

    public String getDetails() {
        return details;
    }

    public String getCategory() {
        return category;
    }

    public String getGrowingConditions() {
        return growingConditions;
    }

    public String getCareTips() {
        return careTips;
    }

    public String getScientificName() {
        return scientificName;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getSource() {
        return source;
    }

    public GrowingGuide getGrowingGuide() {
        return growingGuide;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(plantName);
        dest.writeString(image);
        dest.writeString(details);
        dest.writeString(category);
        dest.writeString(growingConditions);
        dest.writeString(careTips);
        dest.writeString(scientificName);
        dest.writeString(difficultyLevel);
        dest.writeStringList(tags);
        dest.writeString(source);
        dest.writeParcelable(growingGuide, flags);
    }

    public static class GrowingGuide implements Parcelable {
        private List<String> steps;
        private String additionalTips;

        public GrowingGuide(List<String> steps, String additionalTips) {
            this.steps = steps;
            this.additionalTips = additionalTips;
        }

        protected GrowingGuide(Parcel in) {
            steps = in.createStringArrayList();
            additionalTips = in.readString();
        }

        public static final Creator<GrowingGuide> CREATOR = new Creator<GrowingGuide>() {
            @Override
            public GrowingGuide createFromParcel(Parcel in) {
                return new GrowingGuide(in);
            }

            @Override
            public GrowingGuide[] newArray(int size) {
                return new GrowingGuide[size];
            }
        };

        public List<String> getSteps() {
            return steps;
        }

        public String getAdditionalTips() {
            return additionalTips;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeStringList(steps);
            dest.writeString(additionalTips);
        }
    }
}
