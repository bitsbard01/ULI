package com.uli.hackathon.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String GOODS_TYPE = "goodsType";
    public static final String SOURCE_STOP = "sourceStop";
    public static final String DESTINATION_STOP = "destinationStop";
    public static final String ROUTE_TYPE = "routeType";
    public static final String LOCATION_LATITUDE = "locationLatitude";
    public static final String LOCATION_LONGITUDE = "locationLongitude";

    public static final String ACTIVE = "ACTIVE";

    public static final String NOT_AVAILABLE = "NOT_AVAILABLE";

    public static final String PARTIALLY_AVAILABLE = "PARTIALLY_AVAILABLE";

    public static final String AVAILABLE = "AVAILABLE";

    public static final String PLACED = "PLACED";

    public static final String REQUESTED = "REQUESTED";

    public static final String VISIT_REQUEST = "VISIT_REQUEST";

    public static final String ORDER_REQUEST = "ORDER_REQUEST";

    public static final String REJECTED = "REJECTED";

    public static final String ACCEPTED = "ACCEPTED";

    public static final String CANCELLED = "CANCELLED";

    public static final String VISIT_REJECTED = "Visit rejected successfully";

    public static final String VISIT_ACCEPTED = "Visit Accepted successfully";

    public static final String VISIT_NOT_REQUIRED = "Visit no longer required";

    public static final int EARTH_RADIUS = 6371;

    public static final double THRESHOLD_DISTANCE = 50.0 / 111.0;
}
