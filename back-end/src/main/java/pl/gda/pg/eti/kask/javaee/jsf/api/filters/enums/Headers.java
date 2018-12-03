package pl.gda.pg.eti.kask.javaee.jsf.api.filters.enums;

public enum Headers {
    ALL_ENTITIES("all"),
    SAVE_ENTITY("save"),
    UPDATE_ENTITY("put"),
    SELF_ENTITY("self"),
    REMOVE_ENTITY("delete"),
    NEXT_COLLECTION("next"),
    PREVIOUS_COLLECTION("previous");

    private String text;

    private Headers(String text) {
        this.text = text;
    }

    public String getValue() {
        return this.text;
    }
}
