package com.aps.apps.democsvwriter;

import lombok.Builder;
import lombok.Data;
import lombok.With;

/**
 * @author asharma
 * a dummy PoJo class
 */
@Data
@Builder
@With
public class DummyPojo {
    private Integer id;
    private String name;
}
