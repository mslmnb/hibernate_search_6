package com.gala.hibernate_search.config;

import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurationContext;
import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurer;

public class NgramElasticsearchAnalysisConfigurer implements ElasticsearchAnalysisConfigurer {
    @Override
    public void configure(ElasticsearchAnalysisConfigurationContext context) {
        context.tokenFilter( "autocomplete_filter" )
                .type( "edge_ngram" )
                .param( "min_gram", "3" )
                .param("max_gram", "20");

        context.analyzer( "autocomplete" ).custom()
                .tokenizer( "standard" )
                .tokenFilters( "lowercase", "autocomplete_filter" );
    }
}
