package com.example.car_showroom.util;

import com.example.car_showroom.dto.PageableDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class PageableResponseConverter {

    private static final String LIMIT = "limit";
    private static final String PAGE = "page";
    private static final String CONTENT = "content";
    private static final String TOTAL = "total";

    /**
     * Converts the response object to pageable object
     * @param responseObject
     * @param page
     * @param pageLimit
     * @param pageNumber
     * @return
     */
    public ResponseEntity<Object> getPageableResponse(Object responseObject, Page page, int pageLimit, int pageNumber) {
        ResponseEntity<Object> response;
        if (responseObject != null) {
            PageableDTO pageableItemDTO = new PageableDTO();
            // Adding content
            pageableItemDTO.setContent(responseObject);
            //Adding default pageable options into pageable DTO
            if(page != null) {
                getDefaultPageableOptions(page, pageableItemDTO);
            }
            response = new ResponseEntity<>(pageableItemDTO, HttpStatus.OK);
        } else {
            Map<String, Object> pageMap = new HashMap<>();
            pageMap.put(LIMIT, pageLimit);
            pageMap.put(PAGE, pageNumber);
            pageMap.put(TOTAL, 0);
            
            Map<String, Object> resultObj = new HashMap<>();
            resultObj.put(CONTENT, new ArrayList<>());
            resultObj.put(PAGE, pageMap);
            response = new ResponseEntity<>(resultObj, HttpStatus.OK);
        }
        return response;
    }
    private void getDefaultPageableOptions(Page<? extends Object> pageableGroups, PageableDTO pageableDTO) {
        if (pageableGroups != null && pageableDTO != null) {
            pageableDTO.getPage().setLimit(pageableGroups.getPageable().getPageSize());
            pageableDTO.getPage().setTotal(pageableGroups.getTotalElements());
            pageableDTO.getPage().setPage(pageableGroups.getPageable().getPageNumber() + 1);
        }
    }

}
