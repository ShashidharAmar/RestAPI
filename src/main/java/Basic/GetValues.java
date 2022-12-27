package Basic;

import org.testng.Assert;
import io.restassured.path.json.JsonPath;

public class GetValues {

	public static void main(String[] args) {

		JsonPath js = new JsonPath(payload.getDetails());

		// Print No of courses returned by API

		int CourseSize = js.getInt("courses.size()");
		System.out.println("Print No of courses returned by API = " + CourseSize);

		// Print Purchase Amount
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Print Purchase Amount = " + purchaseAmount);

		// Print Title of the first course
		System.out.println("Print Title of the first course = " + js.get("courses[0].title"));

		// Print All course titles and their respective Prices
		System.out.println("Print All course titles and their respective Prices");
		for (int i = 0; i < CourseSize; i++) {

			System.out.println(js.get("courses[" + i + "].title"));
			System.out.println(js.getInt("courses[" + i + "].price"));

		}

		// Print no of copies sold by RPA Course
		for (int i = 0; i < CourseSize; i++) {

			String cousrTitle = js.get("courses[" + i + "].title");
			if (cousrTitle.equalsIgnoreCase("RPA")) {
				System.out.println("Print no of copies sold by RPA Course = " + js.getInt("courses[" + i + "].copies"));
			}

		}

		// Verify if Sum of all Course prices matches with Purchase Amount
		int sum = 0;
		for (int i = 0; i < CourseSize; i++) {
			int price = js.getInt("courses[" + i + "].price");
			int copies = js.getInt("courses[" + i + "].copies");

			sum = sum + (price * copies);
		}

		System.out.println("Verify if Sum of all Course prices matches with Purchase Amount = " + sum);
		Assert.assertEquals(sum, purchaseAmount);

	}
}
