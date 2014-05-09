package com.martinez.winesoftheworld.wines;

import java.io.Serializable;

public class RedWine extends Wine {
	
	public RedWine( String grape ){
		this.grape = grape;
	}
	
	/*@Override
	public String toString() {
		return "RedWine [grape=" + grape + ", getVintage()=" + getVintage()
				+ ", getName()=" + getName() + ", getPrice()=" + getPrice()
				+ ", getRegion()=" + getRegion() + ", getCountry()="
				+ getCountry() + ", getAbv()=" + getAbv() + "]";
	}*/
	public String getGrape(){
		return grape;
	}
}
