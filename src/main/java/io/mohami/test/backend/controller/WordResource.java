package io.mohami.test.backend.controller;

import io.mohami.test.backend.TextFileOutputStream;
import io.mohami.test.backend.service.StatsService;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author andrey
 */

@RestController
public class WordResource {
    private final StatsService statsService;

    @Autowired
    public WordResource(StatsService statsService) {
        this.statsService = statsService;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST , consumes = "text/plain")
    public void upload(HttpServletRequest request, @RequestParam(value = "filename", required = false) String filename) throws IOException {
        File tmp = File.createTempFile("word", ".txt");

        try (OutputStream outStream = new TextFileOutputStream(tmp)) {

            IOUtils.copyLarge(request.getInputStream(), outStream);
        }

        statsService.store(tmp, filename);

    }
    

    @RequestMapping(value = "/stats", method = RequestMethod.GET)
    public Map<String, Long> getStats(HttpServletRequest request, @RequestParam(value = "filename", required = false) String filename) throws IOException {
        Map<String, Long> result = null;
        if (StringUtils.isEmpty(filename)) {
            result = statsService.getAll();
        } else {
            result = statsService.getByFilename(filename);
        }
        if (result == null) {
            throw new FileNotFoundException("Filename " + filename + " not found");
        }
        Map<String, Long>  sorted = result.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return sorted;
    }
    
}
