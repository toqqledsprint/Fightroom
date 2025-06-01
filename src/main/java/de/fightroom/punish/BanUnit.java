package de.fightroom.punish;

import java.util.ArrayList;
import java.util.List;

public enum BanUnit {

    SECOND("Sekunde(n)", 1, "sec"),
    MINUTE("Minute(n)", 60, "min"),
    HOUR("Stunde(n)", 60*60, "hour"),
    DAY("Tag(e)", 24*60*60, "day"),
    WEEK("Woche(n)", 7*24*60*60, "week"),
    MONTH("Monat(e)", 4*7*24*60*60, "month"),
    YEAR("Jahr(e)", 12*4*7*24*60*60, "year");

    private String name;
    private int toSecond;
    private String shortcut;

    BanUnit(String name, int toSecond, String shortcut) {
        this.name = name;
        this.toSecond = toSecond;
        this.shortcut = shortcut;
    }

    public int getToSecond() {
        return toSecond;
    }

    public String getName() {
        return name;
    }

    public String getShortcut() {
        return shortcut;
    }

    public static List<String> getUnitsAsString() {
        List<String> units = new ArrayList<String>();
        for(BanUnit unit : BanUnit.values()) {
            units.add(unit.getShortcut().toLowerCase());
        }
        return units;
    }

    public static BanUnit getUnit(String unit) {
        for(BanUnit units : BanUnit.values()) {
            if(units.getShortcut().toLowerCase().equals(unit.toLowerCase())) {
                return units;
            }
        }
        return null;
    }


}

