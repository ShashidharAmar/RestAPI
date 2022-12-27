package Basic;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

public class ApiTesting {
	
	public static void main(String[] args) {

// Add the place	
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(payload.AddPlace()).when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);
		
		JsonPath js =new JsonPath(response);
		String place_id=js.getString("place_id");
		System.out.println(place_id);
		
		
// Update the place		
		String newAddress="whitefield, bangalure,karnataka";
		given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+place_id+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		
// fetch the place data		
		String getPlaceResponse=given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_id).header("Content-Type","application/json")
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js1=new JsonPath(getPlaceResponse);
		String actualAddress=js1.getString("address");
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress, newAddress);
		
	}

}
