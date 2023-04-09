package org.selenium.pom.constants;

import java.util.Map;

public class Codes {
    public static String getCountryCode(String countryName){
        Map<String, String> countryMap = Map.of(
                "Philippines", "PH",
                "United States (US)", "US"
        );
        return countryMap.get(countryName);
    }

    public static String getStateCode(String stateName){
        Map<String, String> stateMap = Map.of(
                "Ilocos Sur", "ILS",
                "Alabama",	"AL",
                "Alaska",	"AK",
                "American Samoa","AS",
                "Arizona",	"AZ",
                "California",	"CA",
                "Colorado",	"CO",
                "Texas"	,"TX",
                "Florida",	"FL",
                "Connecticut",	"CT");
        return stateMap.get(stateName);
    }
}
//"Arkansas",	"AR",
//"Delaware",	"DE",
//"District Of Colombia",	"DC",

//  "Georgia",	"GA",
//  "Guam",	"GU",
// "Hawaii", "HI",
//  "Idaho", "ID",
//"Illinois",	"IL",
// "Indiana",	"IN",
//  "Iowa",	"IA",
//  "Kansas"	,"KS",
//  "Kentucky",	"KY",
// "Louisiana"	,"LA",
// "Maine"	,"ME",
//   "Maryland",	"MD",
//  "Massachusetts"	,"MA",
// "Michigan"	,"MI",
// "Minnesota"	,"MN",
// "Mississippi",	"MS",
// "Missouri"	,"MO",
// "Montana"	,"MT",
//  "Nebraska",	"NE",
//  "Nevada",	"NV",
// "New Hampshire",	"NH",
// "New Jersey",	"NJ",
// "New Mexico",	"NM",
//  "New York",	"NY",
// "North Carolina",	"NC",
// "North Dakota",	"ND",
// "Northern Mariana Island",	"MP",
//  "Ohio",	"OH",
//  "Oklahoma",	"OK",
// "Oregon",	"OR",
//  "Pennsylvania",	"PA",
// "Puerto Rico",	"PR",
//"Rhode Island",	"RI",
// "South Carolina",	"SC",
// "South Dakota",	"SD",
//"Tennessee",	"TN",
// "Utah",	"UT",
//"Vermont",	"VT",
// "Virginia",	"VA",
// "Virgin IsLands", "VI",
//"Washington", "WA",
//"West Virginia","WV",
//"Wisconsin", "WI",
//"Wyoming", "WY"