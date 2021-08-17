package ro.alina.unidoc.model.type;

public enum DomainType {
    MATEMATICA("MATEMATICA"), INFORMATICA("INFORMATICA"), CTI("CALCULATOARE SI TEHNOLOGIA INFORMATIEI"), BIOSTATISTICA("BIOSTATISTICA");

    private final String name;

    DomainType(String name) {
        this.name = name;
    }

    public String getDomainType() {
        return name;
    }
}
