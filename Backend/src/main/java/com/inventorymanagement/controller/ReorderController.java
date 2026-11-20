
package com.inventorymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventorymanagement.model.ReorderSuggestion;
import com.inventorymanagement.service.ReorderService;

@RestController
@RequestMapping("/reorder")
public class ReorderController {

    @Autowired
    private ReorderService reorderService;

    @GetMapping
    public List<ReorderSuggestion> getReorderSuggestions() {
        return reorderService.getReorderSuggestions();
    }
}