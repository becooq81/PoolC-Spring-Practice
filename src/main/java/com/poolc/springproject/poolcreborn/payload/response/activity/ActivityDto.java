package com.poolc.springproject.poolcreborn.payload.response.activity;

import com.poolc.springproject.poolcreborn.payload.response.user.SimpleUserDto;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
public class ActivityDto {
    private String lead;
    private LocalDate startDate;
    private int hours;
    private int capacity;
    private List<String> tags;
    private String plan;
    private Set<SimpleUserDto> participants;
}
