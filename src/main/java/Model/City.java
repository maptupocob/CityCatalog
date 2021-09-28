package Model;

import java.util.Objects;

public class City {
    private String name;
    private String region;
    private String district;
    private int population;
    private int foundation;

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof City)) return false;
        City cityObj = (City) obj;
        return Objects.equals(this.name, cityObj.name)&&Objects.equals(this.region, cityObj.region);
    }

    @Override
    public String toString() {
        return "City{name='"+this.name+"', region='"+this.region+"', district='"+this.district+"', population="+this.population+", foundation='"+this.foundation+"'}";
    }

    public City(String name, String region, String district, int population, int foundation) {
        this.name = name;
        this.region = region;
        this.district = district;
        this.population = population;
        this.foundation = foundation;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getFoundation() {
        return foundation;
    }

    public void setFoundation(int foundation) {
        this.foundation = foundation;
    }
}
