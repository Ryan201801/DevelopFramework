package com.ryan.model.system;

public enum Components {
    PHOENIX("Phoenix"),
    SMS("SMS"),
    PVE("PVE");

    private String name;
    private Components(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

    public static Components fromString(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        for (Components component : Components.values()) {
            if (component.getName().equalsIgnoreCase(s.trim())) {
                return component;
            }
        }

        throw  new IllegalArgumentException("Unrecognized component name");
    }
}
