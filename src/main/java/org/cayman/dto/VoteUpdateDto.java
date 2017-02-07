package org.cayman.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoteUpdateDto {
    private boolean update;
    private int difference;
}
