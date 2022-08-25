package com.horizon.intl.customer.rewards.service.util;

import java.util.Arrays;

public enum Quarter {
    FIRST("first"), SECOND("second"), THIRD("third"), FOURTH("fourth");

    private String value;

    Quarter(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static Quarter getQuarter(String value) {
        return Arrays.stream(Quarter.values())
                    .filter(quarter -> value.equals(quarter.getValue())).findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid quarter found: "+ value));

    }
}
