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
package sample.dgs.webmvc.schema;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Adam Setch
 */
@DgsComponent
public class Query {

    private static final String[] TITLES = new String[]{
            "Daoguang Emperor",
            "Xianfeng Emperor",
            "Tongzhi Emperor",
            "Guangxu Emperor",
            "Xuantong Emperor"
    };

    private static final String[] NAMES = new String[]{
            "旻寧",
            "奕詝",
            "載淳",
            "載湉",
            "溥儀"
    };

    private static final LocalDate[] REIGN_STARTS = new LocalDate[]{
            LocalDate.of(1820, Month.OCTOBER, 3),
            LocalDate.of(1850, Month.MARCH, 9),
            LocalDate.of(1861, Month.NOVEMBER, 11),
            LocalDate.of(1875, Month.FEBRUARY, 25),
            LocalDate.of(1908, Month.NOVEMBER, 14)
    };

    private static final LocalDate[] REIGN_STOP = new LocalDate[]{
            LocalDate.of(1850, Month.FEBRUARY, 25),
            LocalDate.of(1861, Month.AUGUST, 22),
            LocalDate.of(1875, Month.JANUARY, 12),
            LocalDate.of(1908, Month.NOVEMBER, 14),
            LocalDate.of(1912, Month.FEBRUARY, 12)
    };

    @DgsQuery
    public List<EmperorType> emperors() {
        List<EmperorType> result = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            result.add(
                    new EmperorType(NAMES[i], TITLES[i], REIGN_STARTS[i], REIGN_STOP[i])
            );
        }

        return result;
    }

    @DgsQuery
    public Date now() {
        return new Date();
    }

    @DgsQuery
    public LocalDate yesterday() {
        return LocalDate.now().minusDays(1);
    }

    @DgsQuery
    public LocalDateTime tomorrowMidnight() {
        return LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIDNIGHT);
    }

    @DgsQuery
    public LocalTime noonTime() {
        return LocalTime.NOON;
    }

    @DgsQuery
    public YearMonth springFirstRelease() {
        return YearMonth.of(2002, 10);
    }

    @DgsQuery
    public PongType ping() {
        return new PongType();
    }

}
