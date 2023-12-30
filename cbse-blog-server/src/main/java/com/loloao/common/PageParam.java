package com.loloao.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageParam {

    /*
    {
        "pageSize":20,      // para.getPageSize()
        "pageNum":1,        // para.getPageNum()
        "data":{            // para.getData()
            "no":"admin"    // (String)data.get("no")
        }
    }
     */

    private final static int PAGE_SIZE = 20;
    private final static int PAGE_NUM = 1;

    private int pageSize = PAGE_SIZE;
    private int pageNum = PAGE_NUM;

    private Long userId;

    private HashMap data = new HashMap();
}