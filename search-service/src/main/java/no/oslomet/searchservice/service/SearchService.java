package no.oslomet.searchservice.service;

import no.oslomet.searchservice.model.Search;
import no.oslomet.searchservice.model.Tweet;
import no.oslomet.searchservice.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SearchService {
    @Autowired
    private SearchRepository searchRepository;
    String BASE_URL = "http://localhost:9080/tweets";
    private RestTemplate restTemplate = new RestTemplate();

    public List<Search> getAllSearchs() {
        return searchRepository.findAll();
    }

    public Search getSearchById(long id) {
        return searchRepository.findById(id).get();
    }

    public Search saveSearch(Search search) {
        return searchRepository.save(search);
    }

    public void deleteSearchById(long id) {
        searchRepository.deleteById(id);
    }

    public List<Search> getSearchByUserId(long userId){
        Optional<Search> searches = searchRepository.findSearchesByUserId(userId);
        if(searches.isPresent()){
            return (List<Search>) searches.get();
        }else{
            return null;
        }
    }

    public List<Tweet> searchTweetByText(String searchText)
    {
        return Arrays.stream(
                restTemplate.getForObject(BASE_URL, Tweet[].class)
        ).collect((Collectors.toList()));
    }
}
