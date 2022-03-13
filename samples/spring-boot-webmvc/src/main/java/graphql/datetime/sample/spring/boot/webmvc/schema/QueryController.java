package graphql.datetime.sample.spring.boot.webmvc.schema;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class QueryController {

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

    @QueryMapping
    public List<EmperorType> emperors() {
        List<EmperorType> result = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            result.add(
                    new EmperorType(NAMES[i], TITLES[i], REIGN_STARTS[i], REIGN_STOP[i])
            );
        }

        return result;
    }

    @QueryMapping
    public Date now() {
        return new Date();
    }

    @QueryMapping
    public LocalDate yesterday() {
        return LocalDate.now().minusDays(1);
    }

    @QueryMapping
    public LocalDateTime tomorrowMidnight() {
        return LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIDNIGHT);
    }

    @QueryMapping
    public LocalTime noonTime() {
        return LocalTime.NOON;
    }

    @QueryMapping
    public YearMonth springFirstRelease() {
        return YearMonth.of(2002, 10);
    }

    @QueryMapping
    public PongType ping() {
        return new PongType();
    }

}
