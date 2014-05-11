package com.martinez.winesoftheworld.wines;

import java.io.Serializable;

/**
 * Basic Wine class,
 * Contains the year and name.
 * @author chris
 *
 */
public class Wine implements Serializable  {
	private String name = "";
	private int vintage = 0;
	//private String producerName = "";
	private double price = 0.0;
	private String region = "";
	private String country = "";
	private double abv = 0.0;
	private String tastingNotes = "";
	protected String grape = "";
    private boolean favorite = false;
	public int getVintage() {
		return vintage;
	}
	public void setVintage(int vintage) {
		this.vintage = vintage;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public double getAbv() {
		return abv;
	}
	public void setAbv(double abv) {
		this.abv = abv;
	}
	public String getTastingNotes() {
		return tastingNotes;
	}
	public void setTastingNotes(String tastingNotes) {
		this.tastingNotes = tastingNotes;
	}
	
	public String getGrape(){
		return grape;
	}

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString(){
        return getName();
    }
}
