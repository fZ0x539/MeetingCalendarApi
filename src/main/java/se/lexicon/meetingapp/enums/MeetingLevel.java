package se.lexicon.meetingapp.enums;

public enum MeetingLevel {
    Team("Team"),
    Department("Department"),
    Division("Division"),
    Client("Client");

    private final String displayName;

    MeetingLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
