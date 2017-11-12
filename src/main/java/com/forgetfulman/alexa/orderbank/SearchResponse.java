package com.forgetfulman.alexa.orderbank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponse {

	@JsonProperty("hits")
	public Hits result;

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Hits {

		@JsonProperty("hits")
		public List<Hit> orders;

		public int total;
		public String max_score;

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Hit {

		@JsonProperty("_source")
		public GOBOrder order;

		@JsonProperty("_id")
		public String id;

	}

}
