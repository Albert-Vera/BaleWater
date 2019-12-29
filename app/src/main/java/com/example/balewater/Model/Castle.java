package com.example.balewater.Model;

public class Castle {
    public String getCastleId() {
        return castleId;
    }

    public void setCastleId(String castleId) {
        this.castleId = castleId;
    }

    public String getCastleName() {
        return castleName;
    }

    public void setCastleName(String castleName) {
        this.castleName = castleName;
    }

    public String getCastleDescription() {
        return castleDescription;
    }

    public void setCastleDescription(String castleDescription) {
        this.castleDescription = castleDescription;
    }

    public String getCastleImage() {
        return castleImage;
    }

    public void setCastleImage(String castleImage) {
        this.castleImage = castleImage;
    }

    public String getCastleExplain() {
        return castleExplain;
    }

    public void setCastleExplain(String castleExplain) {
        this.castleExplain = castleExplain;
    }

    private String castleId;
    private String castleName;
    private String castleDescription;
    private String castleExplain;
    private String castleImage;

    public String getCastlePrix() {
        return castlePrix;
    }

    public void setCastlePrix(String castlePrix) {
        this.castlePrix = castlePrix;
    }

    private String castlePrix;
}
