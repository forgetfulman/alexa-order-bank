package com.forgetfulman.alexa.orderbank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.*;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GOBOrder {

	public String orderId;

	public String brand;
	public String vehicleLineModelYear;
	public String catalog;
	public String mpv;
	public String paint;
	public String trim;
	public List<String> series = new ArrayList<>();
	public List<String> options = new ArrayList<>();
	public List<String> exterior = new ArrayList<>();
	public List<String> interior = new ArrayList<>();
	public List<String> noUserChoice = new ArrayList<>();

	public String GTDV01_LAST_UPDT_USER_C;
	public Date GTDV01_LAST_UPDT_S;
	public String GTDV01_SALES_AND_CONTRACTING_KEY_D;
	public Date GTDV01_CONTRACT_Y;
	public Date GTDV01_RETAIL_SALE_Y;

	public String market;
	public String orderingEntity;
	public String destinationEntity;

	public String wholesaleAllocationMonth;
	public String buildWeek;
	public String orderState;
	public String orderSubType;
	public String holdState;
	public boolean discrepency;

	public boolean isOrderAmendable;
	public boolean isSpecAmendable;

	public boolean getIsCancellable() {
		return orderState != null && !orderState.isEmpty() && !orderSubType.isEmpty()
				&& (orderState.equalsIgnoreCase("USCH") || orderState.equalsIgnoreCase("OREC"))
				&& orderSubType.equalsIgnoreCase("SDT");
	}

	public boolean getIsTradeable() {
		return orderState != null && !orderState.isEmpty()
				&& !orderState.equalsIgnoreCase("OCAN")
				&& !orderState.equalsIgnoreCase("VPHD");
	}

	@JsonIgnore
	public void setSpecFeatures(String featureString, Map<String, String> wersToClassification) {

		final List<String> specFeatures = Arrays.asList(featureString.split(","));

		for (final String feature : specFeatures) {
			if (wersToClassification.containsKey(feature)) {
				final String classification = wersToClassification.get(feature);

				switch (classification) {
				case "PAA":
					paint = feature;
					break;
				case "#T#":
					trim = feature;
					break;
				case "Series":
					series.add(feature);
					break;
				case "Options":
					options.add(feature);
					break;
				case "Exterior":
					exterior.add(feature);
					break;
				case "Interior":
					interior.add(feature);
					break;
				case "NoUserChoice":
					noUserChoice.add(feature);
					break;
				default:
					// do nothing
					break;
				}
			}
		}

	}

	@JsonIgnore
	public String getJSONString() {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		String jsonString = "";

		try {
			jsonString = mapper.writeValueAsString(this);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return jsonString;
	}

}
