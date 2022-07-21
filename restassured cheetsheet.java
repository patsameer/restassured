	
	//get request
	Response response = given().
			spec(requestSpec).
			pathParam("id", ID).
		when().
			get("/booking/{id}");
	--------------------
	requestSpec = new RequestSpecBuilder().
                		setBaseUri(readConfigurationFile("Base_URI")).
                		build();
	--------
	verify response code
	responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build(); 
	response.then().spec(responseSpec);	
	---------
	
	@JsonProperty
	private String firstname;
	
	public String getFirstname() {
		return firstname;
	}
	
	@JsonProperty
	private String lastname;
	
	@JsonProperty
	private int totalprice;
	BookingDetails bookingDetails = response.as(BookingDetails.class);
		Assert.assertEquals(bookingDetails.getFirstname(), firstname);
		Assert.assertEquals(bookingDetails.getLastname(), lastname);
		Assert.assertEquals(bookingDetails.getTotalprice(), totalprice);
		
		
################################ Create booking ###########
BookingDetails bookingDetails = new BookingDetails();
		bookingDetails.setFirstname(firstname);
		bookingDetails.setLastname(lastname);
		bookingDetails.setTotalprice(Integer.parseInt(totalprice));
		bookingDetails.setDepositpaid(Boolean.parseBoolean(depositpaid));
		bookingDetails.setAdditionalneeds(additionalneeds);
		
Response response = given().
								spec(requestSpec).
								contentType("application/json").
					            body(bookingDetails).log().body().
					        when().
					        	post("/booking");
								
ASSERT 
//To get the newly created bookign id
		System.out.println(response.then().extract().path("bookingid"));
		newID = response.then().extract().path("bookingid").toString();
		
################# update booking ####################
Response response = given().
			spec(requestSpec).
			header("Content-Type", "application/json").
			header("Accept", "application/json").
			header("Cookie", cookieValue).
	        pathParam("id", IDtoUpdate).
	        body(bookingDetails).log().body().
	    when().
			put("/booking/{id}");
			
	//required parameters for above code
	String cookieValue = "token="+newAuthToken;
	String newAuthToken = AuthToken.post_CreateAuth();
	
	
	//creation of auth token
	public static String post_CreateAuth(){
		
		AllureLogger.logToAllure("Starting the test for POST method for create authentication");
		/*******************************************************
		 * Send a POST request to /auth
		 * and check that the response has HTTP status code 200
		 ******************************************************/
		JSONObject jsonObject = returDefaultPayLoadObject(FrameworkConstants.POSTRequest_AUTH_DEFAULT_REQUEST);
		String username = readConfigurationFile("username");
		String password = readConfigurationFile("password");
		jsonObject.put("password", password);
		jsonObject.put("username", username);
		AllureLogger.logToAllure("Username from config file is : \n"+ username);
		AllureLogger.logToAllure("Password from config file is : \n"+ password);
		
		
		Response response = given().
								spec(requestSpec).
								contentType("application/json").
								body(jsonObject.toJSONString()).
							when().
								post("/auth");
		
		AllureLogger.logToAllure("Asserting the response if the status code returned is 200");
		response.then().spec(responseSpec);
		
		
		String token = response.then().extract().path("token");
		return token;
	}
	
	// returDefaultPayLoadObject
	public static JSONObject returDefaultPayLoadObject(String filePath) {
		// To get the JSON request from external file			
		JSONParser parser = new JSONParser();
		Object obj = null;
		try {
			obj = parser.parse(new FileReader(filePath));
		} catch (Exception e) {
			AllureLogger.logToAllure("Error in JSON object parsing with exception : "+e);
			
		}
		JSONObject jsonObject = (JSONObject) obj;
		return jsonObject;
	}
	
	################## ppatrtial uppdate #####
	Response response = given().
			spec(requestSpec).
			header("Content-Type", "application/json").
			header("Accept", "application/json").
			header("Cookie", cookieValue).
	        pathParam("id", IDtoUpdate).
	        body(setValue).log().body().
	    when().
			patch("/booking/{id}");
			--------------
	String setValue = "{\"firstname\":\"sam\"}";
	
	#################### assertions ####################
	
	response.then().assertThat().body(jsonPathOfValue, hasItem(expectedValue));
	response.then().assertThat().body(jsonPathOfValue, hasSize(size));
	
	//asserting Single Element Vlaue
	response.then().assertThat().body(jsonPathOfValue,equalTo(expectedValue));
	
	//print output log
	response.then().log().all();
	
	