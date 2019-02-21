package com.rudrasagar.springrest.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rudrasagar.springrest.model.Employee;

/**
 * @author rudrasagar.tn
 */

public class SpringRestClient {

	RestTemplate restTemplate;

	public SpringRestClient() {
		super();
		restTemplate = new RestTemplate();
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public static void main(String[] args) {
		new SpringRestClient().getAllEmployees();
		new SpringRestClient().getEmployee();

	}

	public void getEmployee() {
		long start = System.currentTimeMillis();
		ResponseEntity<String> getResponse = restTemplate
				.getForEntity("http://dummy.restapiexample.com/api/v1/employee/21441", String.class);
		long end = System.currentTimeMillis();
		System.out.println("Total time in seconds for webservice response getEmployee:" + (end - start) / 1000.0);
		if (!getResponse.getBody().equals("false")) {
			Employee emp = gsonConversion(getResponse.getBody());
			System.out.println(emp.toString());
		}
	}

	private Employee gsonConversion(String responseBody) {
		Gson gson = new Gson();
		long start = System.currentTimeMillis();
		Employee emp = gson.fromJson(responseBody, Employee.class);
		long end = System.currentTimeMillis();
		System.out.println("Total time for Json to Java POJO Gson:" + (end - start) +"millisec");
		return emp;
	}

	public List<Employee> getAllEmployees() {
		List<Employee> employees = new ArrayList<>();
		
		long start = System.currentTimeMillis();
		ResponseEntity<String> getResponse = restTemplate
				.getForEntity("http://dummy.restapiexample.com/api/v1/employees", String.class);
		long end = System.currentTimeMillis();
		System.out.println("Total time in seconds for webservice response getAllEmployees:" + (end - start) / 1000.0);
		
		
		long start3 = System.currentTimeMillis();
		employees = new Gson().fromJson(getResponse.getBody().toString(), new TypeToken<ArrayList<Employee>>() {
		}.getType());
		long end3 = System.currentTimeMillis();

		System.out.println("Total time in seconds for Gson list populate getAllEmployees:" + (end3 - start3) / 1000.0);
		return employees;
	}

}
