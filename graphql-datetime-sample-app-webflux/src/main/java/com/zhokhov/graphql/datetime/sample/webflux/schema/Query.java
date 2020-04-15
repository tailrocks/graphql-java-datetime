package com.zhokhov.graphql.datetime.sample.webflux.schema;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Component
public class Query implements GraphQLQueryResolver {

    private static String[] TITLES = new String[]{
            "Daoguang Emperor",
            "Xianfeng Emperor",
            "Tongzhi Emperor",
            "Guangxu Emperor",
            "Xuantong Emperor"
    };

    private static String[] NAMES = new String[]{
            "旻寧",
            "奕詝",
            "載淳",
            "載湉",
            "溥儀"
    };

    private static LocalDate[] REIGN_STARTS = new LocalDate[]{
            LocalDate.of(1820, Month.OCTOBER, 3),
            LocalDate.of(1850, Month.MARCH, 9),
            LocalDate.of(1861, Month.NOVEMBER, 11),
            LocalDate.of(1875, Month.FEBRUARY, 25),
            LocalDate.of(1908, Month.NOVEMBER, 14)
    };

    private static LocalDate[] REIGN_STOP = new LocalDate[]{
            LocalDate.of(1850, Month.FEBRUARY, 25),
            LocalDate.of(1861, Month.AUGUST, 22),
            LocalDate.of(1875, Month.JANUARY, 12),
            LocalDate.of(1908, Month.NOVEMBER, 14),
            LocalDate.of(1912, Month.FEBRUARY, 12)
    };

    public CompletableFuture<List<EmperorType>> getEmperors() {
        List<EmperorType> result = IntStream.range(0, NAMES.length)
                .mapToObj(i -> new EmperorType(NAMES[i], TITLES[i], REIGN_STARTS[i], REIGN_STOP[i]))
                .collect(Collectors.toList());
        return Mono.just(result).toFuture();
    }

    public CompletableFuture<Date> getNow() {
        return Mono.just(new Date()).toFuture();
    }

    public CompletableFuture<LocalDate> getYesterday() {
        return Mono.just(LocalDate.now().minusDays(1)).toFuture();
    }

    public CompletableFuture<LocalDateTime> getTomorrowMidnight() {
        return Mono.just(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIDNIGHT)).toFuture();
    }

    public CompletableFuture<LocalTime> getNoonTime() {
        return Mono.just(LocalTime.NOON).toFuture();
    }

}
