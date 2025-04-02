package pt.isel.mpd.weather0;

import org.junit.jupiter.api.Test;
import pt.isel.mpd.weather0.utils.TimeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DateTests {

    @Test
    public void date_to_unix_time() {
        LocalDate start = LocalDate.of(2021, 03, 03);
        LocalDate end = LocalDate.of(2021, 04, 03);

        long ustart = TimeUtils.toUnixTime(start);
        long uend = TimeUtils.toUnixTime(end);

        System.out.println("start=" + ustart);
        System.out.println("end=" + uend);
    }

    @Test
    public void convert_local_date_to_unix_time_and_back_check_same() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        long unixTime = TimeUtils.toUnixTime(now);
        LocalDateTime retrieved = TimeUtils.fromUnixTime(unixTime);
        assertEquals(now, retrieved);
    }


    @Test
    public void unix_time_to_date_time() {
        LocalDateTime date = TimeUtils.fromUnixTime(1646470800);

        System.out.println("date time=" + date);
    }

    @Test
    public void local_date_offset() {
        System.out.println(OffsetDateTime.now().getOffset());
    }
}
