package com.orson.swechallenge.helper;

import com.orson.swechallenge.constants.CommonConfig;
import org.springframework.util.StringUtils;

public class ConstraintsCheck {
    public static boolean checkIfValidSort(String sortName) {

        if (sortName.equalsIgnoreCase("name") || sortName.equalsIgnoreCase("salary") || sortName.equalsIgnoreCase(CommonConfig.DEFAULT_SORT) || sortName == CommonConfig.DEFAULT_SORT) {
            return true;
        } else {
            return false;
        }
    }

    public static String checkIfValidArgumentType(String min, String max, String limit, String offset) {

        try {
            Float min_float = Float.parseFloat(min);
            Float max_float = Float.parseFloat(max);
            Integer.parseInt(limit);
            Integer.parseInt(offset);
            if (!Float.isFinite(min_float) || !Float.isFinite(max_float)) {
                throw new Exception("Invalid Input Type");
            }

        } catch (Exception e) {
            //  Block of code to handle errors
            return "Invalid Input Type";
        }

        return "Valid";
    }

    public static String nullToDefault(String initial, String defaultVal) {

        if (initial == null || initial.equals("null") || initial.isEmpty()) {
            return defaultVal;
        }

        return initial;
    }
}
