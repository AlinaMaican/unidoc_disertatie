package ro.alina.unidoc.model.type;

public enum StudyYearType {
    I("ANUL 1"), II("ANUL 2"), III("ANUL 3"), IV("ANUL 4");

    private final String name;

    StudyYearType(String name) {
        this.name = name;
    }

    public String getStudyYearType() {
        return name;
    }
}
