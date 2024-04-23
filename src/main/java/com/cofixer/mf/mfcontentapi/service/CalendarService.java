package com.cofixer.mf.mfcontentapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CalendarService {

    private final HolidayManager holidayManager;
}
