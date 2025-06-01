package pl.edu.pja.tpo_12.service;

import pl.edu.pja.tpo_12.model.Publisher;

import java.util.List;

public interface PublisherService {
    List<Publisher> getAllPublishers();
    Publisher getPublisherById(Long id);
    Publisher savePublisher(Publisher publisher);
    Publisher updatePublisher(Long id, Publisher publisher);
    void deletePublisher(Long id);
    List<Publisher> searchPublishers(String query);
    List<Publisher> getPublishersByCountry(String country);
}