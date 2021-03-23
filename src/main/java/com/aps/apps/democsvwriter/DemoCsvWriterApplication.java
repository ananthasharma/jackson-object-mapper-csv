package com.aps.apps.democsvwriter;

import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringWriter;
import java.util.UUID;

/**
 *
 * sample appliction which writes a java PoJo object as csv
 *
 * @author asharma
 */
@Slf4j
@SpringBootApplication
public class DemoCsvWriterApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoCsvWriterApplication.class, args);
    }

    /**
     * method that does the deed
     * @throws IOException
     */
    @PostConstruct
    public void createSampleCsv() throws IOException {
        log.info("printing sample csv");
        CsvMapper mapper = new CsvMapper()
                .configure(CsvGenerator.Feature.ALWAYS_QUOTE_STRINGS, true)
                .configure(CsvParser.Feature.ALLOW_TRAILING_COMMA, false);
        CsvSchema schema = mapper.schemaFor(DummyPojo.class).withColumnReordering(true);
        schema = schema.withColumnSeparator(',');
        schema = schema.withHeader();
        log.info("Writing to console");
        StringWriter sw = new StringWriter();
        for (var i = 0; i < 100; i++) {
            DummyPojo dp = new DummyPojo(12 + i, UUID.randomUUID().toString());
            if (i > 0) {
                // don't write header after the first row is printed
                schema = schema.withoutHeader();
            }
            mapper.writer(schema).writeValue(sw, dp);
        }
        log.info("whole file: {}{}", System.lineSeparator(), sw.getBuffer().toString());
        log.info("[done] printing sample csv");
    }
}
