package com.martinez.winesoftheworld.wines;

import android.content.res.AssetManager;
import android.os.Parcelable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.Serializable;

public class WineControl implements Serializable {

    ArrayList<Wine> wines = new ArrayList<Wine>();

    public WineControl( InputStream fis ){
		try{
			BufferedReader reader = new BufferedReader( new InputStreamReader( fis ) );
			String line = null;
			int counter = 0;
			while (( line = reader.readLine()) != null ) {
				if( counter == 0 ){
					counter++;
					continue;
				}
				String[] explodedLine = line.split(",");
				String name = explodedLine[0].trim();
				String type = explodedLine[1].trim();
				String grape = explodedLine[2].trim();
				String vintage = explodedLine[3].trim();
				String price = explodedLine[4].trim();
				String region = explodedLine[5].trim();
				String country = explodedLine[6].trim();
				String abv = explodedLine[7].trim();
                String notes = "";
                String favorite = "";
                try{
				    notes = explodedLine[8].trim();
                } catch (Exception e){
                }
                try{
                    favorite = explodedLine[9].trim();
                } catch (Exception e){

                }
				Wine wine = null;
				if( type.equals("white")){
					wine = new WhiteWine( grape );
				} else if( type.equals("red") ){
					wine = new RedWine( grape );
				} else if( type.equals("rose") ){
					
				} else if( type.equals("fortified" ) ){
					
				}
				
				wine.setName(name);
				wine.setVintage( Integer.parseInt(vintage));
				wine.setPrice( Double.parseDouble(price));
				wine.setRegion(region);
				wine.setCountry(country);
				wine.setAbv( Double.parseDouble(abv));
				wine.setTastingNotes( notes );
				wines.add(wine);
				counter++;
			}
			reader.close();
			
		} catch (Exception e){
			System.out.println("Something happened.");
			e.printStackTrace();
		}
	}

    public ArrayList<Wine> searchByGrape(  String grape ){
		ArrayList<Wine> searchedWines = new ArrayList<Wine>();
		
		for( Wine wine: wines ){
			if( wine.getGrape().toLowerCase().contains(grape.toLowerCase()) ){
				searchedWines.add( wine );
			}
		}
		
		return searchedWines;
	}

    public ArrayList<Wine> searchByVintage(  int vintage ){
		ArrayList<Wine> searchedWines = new ArrayList<Wine>();
		
		for( Wine wine: wines ){
			if( wine.getVintage() == vintage ){
				searchedWines.add( wine );
			}
		}
		
		return searchedWines;
	}

    public ArrayList<Wine> searchByVintage(  int lowVintage, int highVintage ){
        ArrayList<Wine> searchedWines = new ArrayList<Wine>();

        for( Wine wine: wines ){
            if( lowVintage != 0 && highVintage != 0 ){
                if( wine.getVintage() >= lowVintage && wine.getVintage() <= highVintage ){
                    searchedWines.add( wine );
                }
            } else if (lowVintage == 0 ){
                if( wine.getVintage() <= highVintage ){
                    searchedWines.add( wine );
                }
            } else if ( highVintage == 0 ){
                if( wine.getVintage() >= lowVintage ){
                    searchedWines.add( wine );
                }
            }
        }

        return searchedWines;
    }

    public ArrayList<Wine> searchByPrice(  double lowerPrice, double higherPrice ){
		ArrayList<Wine> searchedWines = new ArrayList<Wine>();
		
		for( Wine wine: wines ){
			if( lowerPrice == 0.0 ){
				if( wine.getPrice() <= higherPrice ){
					searchedWines.add(wine);
				}
			} else if ( higherPrice == 0.0 ){
				if( wine.getPrice() >= lowerPrice ){
					searchedWines.add(wine);
				}
			} else {
				if( wine.getPrice() >= lowerPrice && wine.getPrice() <= higherPrice ){
					searchedWines.add(wine);
				}
			}
		}
		
		return searchedWines;
	}

    public ArrayList<Wine> searchByRegion(  String region ){
		ArrayList<Wine> searchedWines = new ArrayList<Wine>();
		
		for( Wine wine: wines ){
			if( wine.getRegion().toLowerCase().contains( region.toLowerCase() ) ){
				searchedWines.add( wine );
			}
		}
		
		return searchedWines;
	}

    public ArrayList<Wine> searchByCountry(  String country ){
  	ArrayList<Wine> searchedWines = new ArrayList<Wine>();

		for( Wine wine: wines ){
            if( country.equalsIgnoreCase("u.s.") || country.equalsIgnoreCase("united states") ){
                if( wine.getCountry().toLowerCase().contains( "u.s.".toLowerCase() ) //special case for abbreviated united states
                        || wine.getCountry().toLowerCase().contains("united states".toLowerCase() ) ){
                    searchedWines.add( wine );
                }
            } else {
                if( wine.getCountry().toLowerCase().contains( country.toLowerCase() ) ){
                    searchedWines.add( wine );
                }
            }
		}

		return searchedWines;
	}
	
	public ArrayList<Wine> searchByTastingNotes(  String ... notes ){
		ArrayList<Wine> searchedWines = new ArrayList<Wine>();
		
		for( Wine wine: wines ){
			for( String note: notes ){
                // Escape the special character with \\
                String[] individualNotes = wine.getTastingNotes().split("\\|");
				for (String tastingNote : individualNotes){
                    //System.out.println(tastingNote.trim()); //DEBUG
					if( tastingNote.contains( note ) ){
						searchedWines.add(wine);
					}
				}
			}
			
		}
		
		return searchedWines;
	}

    public ArrayList<Wine> searchByName( String name ){
        ArrayList<Wine> searchedWines = new ArrayList<Wine>();
        for( Wine wine: wines){
            if( wine.getName().contains(name)){
                searchedWines.add( wine );
            }
        }
        return searchedWines;
    }

    public ArrayList<Wine> getWines(){
        ArrayList<Wine> wines = new ArrayList<Wine>();


        wines = (ArrayList<Wine>)this.wines.clone();

        return wines;
    }
}
