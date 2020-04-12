package com.zhokhov.graphql.datetime.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author <a href='mailto:dgmneto@gmail.com'>Divino G. de Menezes Neto</a>
 */
@ConfigurationProperties(prefix = "graphql.datetime.scalars")
public class GraphQLDateTimeProperties {

    private ScalarDefinition date = new ScalarDefinition();
    private ScalarDefinition localDate = new ScalarDefinition();
    private ScalarDefinition localDateTime = new ScalarDefinition();
    private ScalarDefinition localTime = new ScalarDefinition();
    private ScalarDefinition offsetDateTime = new ScalarDefinition();
    private boolean zoneConversionEnabled = false;

    ScalarDefinition getDate() {
        return date;
    }

    ScalarDefinition getLocalDate() {
        return localDate;
    }

    ScalarDefinition getLocalDateTime() {
        return localDateTime;
    }

    ScalarDefinition getLocalTime() {
        return localTime;
    }

    ScalarDefinition getOffsetDateTime() {
        return offsetDateTime;
    }

    public static class ScalarDefinition {

        private String scalarName;

        String getScalarName() {
            return scalarName;
        }

        public void setScalarName(String scalarName) {
            this.scalarName = scalarName;
        }

    }

    public void setDate(ScalarDefinition date) {
        this.date = date;
    }

    public void setLocalDate(ScalarDefinition localDate) {
        this.localDate = localDate;
    }

    public void setLocalDateTime(ScalarDefinition localDateTime) {
        this.localDateTime = localDateTime;
    }

    public void setLocalTime(ScalarDefinition localTime) {
        this.localTime = localTime;
    }

    public void setOffsetDateTime(ScalarDefinition offsetDateTime) {
        this.offsetDateTime = offsetDateTime;
    }

    public boolean isZoneConversionEnabled() {
        return zoneConversionEnabled;
    }

    public void setZoneConversionEnabled(boolean zoneConversionEnabled) {
        this.zoneConversionEnabled = zoneConversionEnabled;
    }
}
