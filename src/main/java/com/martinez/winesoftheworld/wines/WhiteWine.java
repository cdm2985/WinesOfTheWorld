package com.martinez.winesoftheworld.wines;

import java.io.Serializable;

public class WhiteWine extends Wine{
	public WhiteWine( String grape ){
		this.grape = grape;
	}

	/*@Override
	public String toString() {
		return "WhiteWine [grape=" + grape + ", getVintage()=" + getVintage()
				+ ", getName()=" + getName() + ", getPrice()=" + getPrice()
				+ ", getRegion()=" + getRegion() + ", getCountry()="
				+ getCountry() + ", getAbv()=" + getAbv() + "]";
	}*/

	public String getGrape(){
		return grape;
	}
	
}
