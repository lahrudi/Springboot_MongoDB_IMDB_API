package com.spring.data.mongodb.controller;

import com.spring.data.mongodb.model.Principal;
import com.spring.data.mongodb.model.PrincipalWithName;
import com.spring.data.mongodb.model.Title;
import com.spring.data.mongodb.services.PrincipalService;
import com.spring.data.mongodb.services.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;

@RestController
public class AllEndPointController {

    private final PrincipalService principalService;
    private final TitleService titleService;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public AllEndPointController(MongoTemplate mongoTemplate, PrincipalService principalService, TitleService titleService) {
        this.principalService = principalService;
        this.titleService = titleService;
        this.mongoTemplate = mongoTemplate;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/people/{tconst}/titles")
    public List<Title> getAllTitles(@PathVariable String tconst) {

        return titleService.findByTconst(tconst);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/titles/{tconst}/people")
    public List<PrincipalWithName> getAllPeople(@PathVariable String tconst) {
        MatchOperation filterByNconst = match(Criteria.where("tconst").is(tconst));
        LookupOperation nameLookup = LookupOperation.newLookup()
                .from("names")
                .localField("nconst")
                .foreignField("nconst")
                .as("person");

        Aggregation aggregation = Aggregation.newAggregation(
                filterByNconst,
                nameLookup
        );

        AggregationResults<PrincipalWithName> results = mongoTemplate.aggregate(aggregation, "principals_mapping", PrincipalWithName.class);

        List<PrincipalWithName> mappedResults = results.getMappedResults();
        for (PrincipalWithName p: mappedResults) {
            p.person.remove("_id");
        }

        return mappedResults;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/principal/{tconst}")
    public List<Principal> getAllPrincipal(@PathVariable String tconst) {
        return principalService.findByTconst(tconst);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/titles/{tconst}/crew")
    public List<PrincipalWithName> getAllCrew(@PathVariable String tconst) {
        MatchOperation filterByNconst = match(Criteria.where("tconst").is(tconst));
        MatchOperation excludeCast = match(Criteria.where("category").ne("actress").andOperator(Criteria.where("category").ne("actor")));
        LookupOperation nameLookup = LookupOperation.newLookup()
                .from("names")
                .localField("nconst")
                .foreignField("nconst")
                .as("person");

        Aggregation aggregation = Aggregation.newAggregation(
                filterByNconst,
                excludeCast,
                nameLookup
        );

        AggregationResults<PrincipalWithName> results = mongoTemplate.aggregate(aggregation, "principals_mapping", PrincipalWithName.class);

        List<PrincipalWithName> mappedResults = results.getMappedResults();
        for (PrincipalWithName p: mappedResults) {
            p.person.remove("_id");
        }

        return mappedResults;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/titles/{tconst}/cast")
    public List<PrincipalWithName> getAllCast(@PathVariable String tconst) {
        MatchOperation filterByNconst = match(Criteria.where("tconst").is(tconst));
        MatchOperation includeCast = match(new Criteria().orOperator(Criteria.where("category").is("actor"), Criteria.where("category").is("actress")));
        LookupOperation nameLookup = LookupOperation.newLookup()
                .from("names")
                .localField("nconst")
                .foreignField("nconst")
                .as("person");

        Aggregation aggregation = Aggregation.newAggregation(
                filterByNconst,
                includeCast,
                nameLookup
        );

        AggregationResults<PrincipalWithName> results = mongoTemplate.aggregate(aggregation, "principals_mapping", PrincipalWithName.class);

        List<PrincipalWithName> mappedResults = results.getMappedResults();
        for (PrincipalWithName p: mappedResults) {
            p.person.remove("_id");
        }

        return mappedResults;
    }

}
