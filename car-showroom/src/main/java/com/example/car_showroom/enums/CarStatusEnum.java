package com.example.car_showroom.enums;

import com.example.car_showroom.Exception.CustomException;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public enum CarStatusEnum {
    AVAILABLE("Available"),
    UNAVAILABLE("Unavailable"),
    SOLD("Sold");
    private String status;

    CarStatusEnum(String status) {
        this.status = status;
    }

    private static final Map<String, CarStatusEnum> STATUS_MAP = Stream.of(values()).collect(Collectors.toMap(CarStatusEnum::toString, Function.identity()));

    /**
     * Get status value of the enum type
     *
     * @return - status value of the selected enum.
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method is used to convert the string value into enum type.
     * Null will be returned if it is not exist.
     *
     * @param value - A string value which need to be converted into enum type
     * @return - StatusEnum object based on the given value.
     */
    public static CarStatusEnum toValue(String value) {
        if (value != null) {
            for (CarStatusEnum statusEnum : CarStatusEnum.values()) {
                if (statusEnum.getStatus().equalsIgnoreCase(value)) {
                    return statusEnum;
                }
            }
        }
        return null;
    }

    /**
     * This method is used to look up by name
     *
     * @param name - enum name
     * @return - StatusEnum object based on the given name.
     */
    public static CarStatusEnum lookupByName(String name) {
        CarStatusEnum statusEnum = STATUS_MAP.get(name);
        return Optional.ofNullable(statusEnum).orElseThrow(() -> new CustomException(String.format("'%s' has no corresponding value. Accepted values: %s", name, Arrays.asList(values()))));
    }
}
