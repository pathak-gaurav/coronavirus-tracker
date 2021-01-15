package ca.gauravpathak.service;

import ca.gauravpathak.model.LocationStats;
import lombok.Getter;
import lombok.extern.java.Log;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@Log
@Getter
public class CoronavirusTrackerService {

    @Value("${URL}")
    private String URL;

    private List<LocationStats> allStats = new ArrayList<>();

    @PostConstruct
    @Scheduled(fixedRate=50000)
    public void fetchCoronavirusData() throws IOException, InterruptedException {
        List<LocationStats> temporaryStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader reader = new StringReader(response.body());
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader);
        for (CSVRecord record : records) {
            if(record.get("Country/Region").equalsIgnoreCase("Canada")) {
                LocationStats locationStats = new LocationStats();
                locationStats.setCountry(record.get("Country/Region"));
                locationStats.setProvince(record.get("Province/State"));
                locationStats.setLatestTotalCases(Integer.parseInt(record.get(record.size() - 1)));
                locationStats.setCasesIncreased(Integer.parseInt(record.get(record.size() - 1)) - Integer.parseInt(record.get(record.size() - 2)));
                temporaryStats.add(locationStats);
            }
        }
        this.allStats = temporaryStats;
    }
}
