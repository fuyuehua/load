package com.rip.load.pojo.operatorCreditReport;

/**
 * 出行信息
 */
public class TripAnalysis {

    /**
     * 出发时间
     */
    private String departureDate;
    /**
     * 回程时间
     */
    private String returnDate;
    /**
     * 出发地
     */
    private String departurePlace;
    /**
     * 回程地
     */
    private String destinationPlace;

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getDeparturePlace() {
        return departurePlace;
    }

    public void setDeparturePlace(String departurePlace) {
        this.departurePlace = departurePlace;
    }

    public String getDestinationPlace() {
        return destinationPlace;
    }

    public void setDestinationPlace(String destinationPlace) {
        this.destinationPlace = destinationPlace;
    }
}
