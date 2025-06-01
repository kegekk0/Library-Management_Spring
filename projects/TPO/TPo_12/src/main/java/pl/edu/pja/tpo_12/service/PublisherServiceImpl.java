package pl.edu.pja.tpo_12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pja.tpo_12.model.Publisher;
import pl.edu.pja.tpo_12.repository.PublisherRepository;

import java.util.List;

@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    @Override
    public Publisher getPublisherById(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found with id: " + id));
    }

    @Override
    public Publisher savePublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    @Override
    public Publisher updatePublisher(Long id, Publisher publisher) {
        Publisher existingPublisher = getPublisherById(id);
        existingPublisher.setName(publisher.getName());
        existingPublisher.setAddress(publisher.getAddress());
        existingPublisher.setCountry(publisher.getCountry());
        return publisherRepository.save(existingPublisher);
    }

    @Override
    public void deletePublisher(Long id) {
        publisherRepository.deleteById(id);
    }

    @Override
    public List<Publisher> searchPublishers(String query) {
        return publisherRepository.findByNameContainingIgnoreCase(query);
    }

    @Override
    public List<Publisher> getPublishersByCountry(String country) {
        return publisherRepository.findByCountry(country);
    }
}