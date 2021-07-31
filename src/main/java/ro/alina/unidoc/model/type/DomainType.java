package ro.alina.unidoc.model.type;

public enum DomainType {
    MATE("MATEMATICA"), INFO("INFORMATICA"), CTI("CALCULATOARE SI TEHNOLOGIA INFORMATIEI"), BIOSTATISTICA("BIOSTATISTICA");

    private final String name;

    DomainType(String name) {
        this.name = name;
    }

    public String getDomainType() {
        return name;
    }
}
