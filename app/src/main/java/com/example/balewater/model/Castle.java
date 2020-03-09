package com.example.balewater.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Castle {

    @PrimaryKey(autoGenerate = true)
    public  int castleId;

    private String castleName;
    private String castleDescription;
    private String castleExplain;
    private String castleImage;
    private String precioCoste, precioVenta;

    public Castle() {
    }

    public Castle(String castleName, String castleDescription, String castleExplain, String castleImage, String precioCoste, String precioVenta) {
        this.castleName = castleName;
        this.castleDescription = castleDescription;
        this.castleExplain = castleExplain;
        this.castleImage = castleImage;
        this.precioCoste = precioCoste;
        this.precioVenta = precioVenta;
    }

    public int getCastleId() {
        return castleId;
    }

    public void setCastleId(int castleId) {
        this.castleId = castleId;
    }

    public String getPrecioCoste() {
        return precioCoste;
    }

    public void setPrecioCoste(String precioCoste) {
        this.precioCoste = precioCoste;
    }

    public String getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(String precioVenta) {
        this.precioVenta = precioVenta;
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

}
