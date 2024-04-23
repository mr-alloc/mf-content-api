package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.constant.KoreanHoliday;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;


@Slf4j
@Service
@RequiredArgsConstructor
public class HolidayManager {


    public Map<Integer, List<KoreanHoliday>> getHolidayMap() {
        return getAllHolidays().stream()
                .collect(groupingBy(KoreanHoliday::getMonth));
    }

    @Cacheable("holiday_map")
    public List<KoreanHoliday> getAllHolidays() {
        log.info("get all holidays");
        return List.of(KoreanHoliday.values());
    }
}
