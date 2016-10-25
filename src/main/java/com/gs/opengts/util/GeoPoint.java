package com.gs.opengts.util;

public class GeoPoint {

    // ------------------------------------------------------------------------
    public static final double EPSILON            = 1.0E-7;
    public static final double MAX_LATITUDE       = 90.0;
    public static final double MIN_LATITUDE       = -90.0;
    public static final double MAX_LONGITUDE      = 180.0;
    public static final double MIN_LONGITUDE      = -180.0;
    public static final String PointSeparator     = "/";
    public static final char   PointSeparatorChar = '/';

    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * An immutable invalid GeoPoint
     */
    public static final GeoPoint INVALID_GEOPOINT = new GeoPoint(0.0, 0.0).setImmutable();

    // ------------------------------------------------------------------------
    private boolean immutable = false;
    private double  latitude  = 0.0;
    private double  longitude = 0.0;

    /**
     * Constructor.
     * This creates a GeoPoint with latitude=0.0, and longitude=0.0
     */
    public GeoPoint() {
        super();
        this.latitude  = 0.0;
        this.longitude = 0.0;
    }
    
    /**
     * Constructor.
     * This creates a new GeoPoint with the specified latitude/longitude.
     * @param latitude  The latitude
     * @param longitude The longitude
     */
    public GeoPoint(double latitude, double longitude) {
        this();
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }
    
	/**
     * Returns true if the specified latitude/longitude are valid, false otherwise
     * @param lat  The latitude
     * @param lon  The longitude
     * @return True if the specified latitude/longitude are valid, false otherwise
     */
    public static boolean isValid(double lat, double lon) {
        double latAbs = Math.abs(lat);
        double lonAbs = Math.abs(lon);

        if (latAbs >= MAX_LATITUDE) {

            // invalid latitude
            return false;
        } else if (lonAbs >= MAX_LONGITUDE) {

            // invalid longitude
            return false;
        } else if ((latAbs <= 0.0001) && (lonAbs <= 0.0001)) {

            // small square off the coast of Africa (Ghana)
            return false;
        } else {
            return true;
        }
    }

    /**
     * Sets the Latitude in degrees
     * @param lat  The Latitude
     */
    public void setLatitude(double lat) {

        // immutable?
        if (this.isImmutable()) {
            Print.logError("This GeoPoint is immutable, changing Latitude denied!");
        } else {
            this.latitude = lat;
        }
    }

    /**
     * Gets the Latitude in degrees
     * @return The Latitude in degrees
     */
    public double getLatitude() {
        return this.latitude;
    }
    
    /**
     * Sets the Longitude in degrees
     * @param lon  The Longitude
     */
    public void setLongitude(double lon) {
        if (this.isImmutable()) {
            Print.logError("This GeoPoint is immutable, changing Longitude denied!");
        } else {
            this.longitude = lon;
        }
    }

    /**
     * Gets the Longitude in degrees
     * @return The Longitude in degrees
     */
    public double getLongitude() {
        return this.longitude;
    }
    
    // ------------------------------------------------------------------------

    /**
     * Set this GeoPoint as Immutable
     * @return This GeoPoint
     */
    public GeoPoint setImmutable() {
        this.immutable = true;

        return this;    // to allow chaining
    }

    /**
     * Returns true if this GeoPoint is immutable
     * @return True if this GeoPoint is immutable, false otherwise.
     */
    public boolean isImmutable() {
        return this.immutable;
    }

    // ------------------------------------------------------------------------
}
