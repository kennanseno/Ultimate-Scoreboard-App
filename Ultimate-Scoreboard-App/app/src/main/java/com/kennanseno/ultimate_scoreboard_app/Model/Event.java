package com.kennanseno.ultimate_scoreboard_app.Model;

public class Event {

    private int id;
    private String name;
    private String venue;
    private String startDate;
    private String endDate;
    private String eventOrganizer;

    public Event(){

    };

    public Event(int id, String name, String venue, String startDate, String endDate, String eventOrganizer) {
        this.id = id;
        this.name = name;
        this.venue = venue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventOrganizer = eventOrganizer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEventOrganizer() {
        return eventOrganizer;
    }

    public void setEventOrganizer(String eventOrganizer) {
        this.eventOrganizer = eventOrganizer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", venue='" + venue + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", eventOrganizer='" + eventOrganizer + '\'' +
                '}';
    }
}
