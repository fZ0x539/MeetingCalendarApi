package se.lexicon.meetingapp.enums;

public enum MeetingLevel {
    TEAM("Team"),
    DEPARTMENT("Department"),
    DIVISION("Division"),
    CLIENT("Client");

    private final String displayName;

    MeetingLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
