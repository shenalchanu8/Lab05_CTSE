package com.lab.item;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final List<Map<String, Object>> items = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public ItemController() {
        // Pre-populate with sample items
        addSampleItem("Book");
        addSampleItem("Laptop");
        addSampleItem("Phone");
    }

    private void addSampleItem(String name) {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("id", idCounter.getAndIncrement());
        item.put("name", name);
        items.add(item);
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getItems() {
        return ResponseEntity.ok(items);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addItem(@RequestBody Map<String, Object> body) {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("id", idCounter.getAndIncrement());
        item.put("name", body.get("name"));
        items.add(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getItem(@PathVariable int id) {
        return items.stream()
                .filter(i -> i.get("id").equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
