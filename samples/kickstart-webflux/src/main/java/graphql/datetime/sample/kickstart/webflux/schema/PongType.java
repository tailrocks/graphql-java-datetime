/*
 * Copyright 2017 Alexey Zhokhov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package graphql.datetime.sample.kickstart.webflux.schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @author Alexey Zhokhov
 */
public class PongType {

    private final Date date;
    private final LocalDate localDate;
    private final LocalDateTime localDateTime;
    private final LocalTime localTime;
    private final OffsetDateTime offsetDateTime;

    public PongType() {
        date = new Date(1499667166754L);
        localDate = LocalDate.of(2017, 1, 1);
        localTime = LocalTime.MIDNIGHT;
        localDateTime = LocalDateTime.of(localDate, localTime);
        offsetDateTime = OffsetDateTime.of(localDate, localTime, ZoneOffset.UTC);
    }

    public Date getDate() {
        return date;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public OffsetDateTime getOffsetDateTime() {
        return offsetDateTime;
    }

}