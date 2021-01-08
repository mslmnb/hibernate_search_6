package com.gala.hibernate_search.config;

import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurationContext;
import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurer;
import org.springframework.stereotype.Component;

@Component
public class MyElasticsearchAnalysisConfigurer implements ElasticsearchAnalysisConfigurer {
    @Override
    public void configure(ElasticsearchAnalysisConfigurationContext context) {
        context.analyzer( "english1" ).custom()
                                        .tokenizer( "standard" )
                                        .tokenFilters( "lowercase", "snowball_english", "asciifolding" );

        context.tokenFilter( "snowball_english" )
                                        .type( "snowball" )
                                        .param( "language", "English" );

        context.analyzer( "name1" ).custom()
                                     .tokenizer( "standard" )
                                     .tokenFilters( "lowercase", "asciifolding" );
    }
}

    /* в результате получим следующую конфигурацию индекса
    если индекс уже существовал и пытаюсь добавить новый анализатор или фильтр может выйти ошибка (приложение не грузиться
    и ссылается на неизвестный анализатор или фильтр),
    тогда потребуется удалить индекс из эластика и запустить приложение заново, затем переиндексировать записи БД
    {
    "book-000001": {
        "settings": {
            "index": {
                "routing": {
                    "allocation": {
                        "include": {
                            "_tier_preference": "data_content"
                        }
                    }
                },
                "number_of_shards": "1",
                "provided_name": "book-000001",
                "creation_date": "1610097443163",
                "analysis": {
                    "filter": {
                        "snowball_english": {
                            "type": "snowball",
                            "language": "English"
                        }
                    },
                    "analyzer": {
                        "name1": {
                            "filter": [
                                "lowercase",
                                "asciifolding"
                            ],
                            "type": "custom",
                            "tokenizer": "standard"
                        },
                        "english1": {
                            "filter": [
                                "lowercase",
                                "snowball_english",
                                "asciifolding"
                            ],
                            "type": "custom",
                            "tokenizer": "standard"
                        }
                    }
                },
                "number_of_replicas": "1",
                "uuid": "yQUw2YADR0alameTwyEBeA",
                "version": {
                    "created": "7100099"
                }
            }
        }
    }
}
     */


