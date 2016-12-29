package com.github.walterfan.example.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * This class captures information about the PSTN number that Spark clients
 * can use to dial in to the Locus using Spark PSTN services
 *
 */
public class PstnNumber {
    private final String number;
    private final CountryCode countryCode;
    private final boolean tollfree;

    @JsonCreator
    public PstnNumber(@JsonProperty("number") String number,
                      @JsonProperty("countryCode") CountryCode countryCode,
                      @JsonProperty("tollfree") boolean tollfree) {
        this.number = number;
        this.countryCode = countryCode;
        this.tollfree = tollfree;
    }

    /**
     * The E.164 number (e.g. +14088937712)
     */
    public String getNumber() {
        return number;
    }

    /*
    * returns the ISO 3166 country code (alpha-2, alpha-3, or numeric)
    * that indicates where where this number is located
    * For example, Brazil could be one of BR, BRA, or 076
    * @see http://www.iso.org/iso/country_codes
     */
    public CountryCode getCountryCode() {
        return countryCode;
    }

    /**
     *
     * @return true if the PSTN dial-in number is considered "toll free" in the
     * country where it is dialed from
     */
    public boolean isTollFree() {
        return tollfree;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof PstnNumber
                && Objects.equals(number, ((PstnNumber) other).getNumber())
                && Objects.equals(countryCode, ((PstnNumber) other).getCountryCode())
                && Objects.equals(tollfree, ((PstnNumber) other).isTollFree())
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, countryCode, tollfree);
    }
}

