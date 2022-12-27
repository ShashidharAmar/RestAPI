package BookValidation;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import Basic.payload;
import Helper.Random_Numbers;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;


public class AddBooks {
	
	
	@Test(dataProvider="BookData")
	public void addBook(String isbn,int aisle)
	{
		RestAssured.baseURI="http://216.10.245.166";
		String response = given().log().all().header("Content-Type","application/json").
        body(payload.addBooks( isbn, aisle+Random_Numbers.m1())).
        when().post("Library/Addbook.php").
        then().log().all().assertThat().statusCode(200).extract().response().asString();
        
        System.out.println(response);
         
        JsonPath js=new JsonPath(payload.addBooks(isbn, aisle));
        String bookName = js.get("name");
        System.out.println("BOOKS NAME = " +bookName);
          
	}
	
	@DataProvider(name = "BookData")
	public Object [][] getdata()
	{
		return new Object[][] {
			                    {"Book1",1212},
			                    {"Book2",1221},
			                    {"Book3",1232},
			                    {"Book4",1241}
			                  };
		
	}

}
