package com.example.demo;



import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.example.demo.controller.TaskController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DemoApplication {

    private final TaskController taskController;

    DemoApplication(TaskController taskController) {
        this.taskController = taskController;
    }

	@SuppressWarnings("null")
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

	
		
		
		
		
//		Given an array arr[] of size n, the task is to return an equilibrium index (if any) or -1 if no equilibrium index exists.
//				The equilibrium index of an array is an index such that the sum of all elements at lower indexes equals the sum of all elements at higher indexes.
//				 
//				Input: arr[] = [1, 2, 0, 3]
//				Output: 2
//				Explanation: The sum of left of index 2 is 1 + 2 = 3 and sum on right of index 2 is 3.
//
//				Input: arr[] = [1, 1, 1, 1]
//				Output: -1 
//				Explanation: There is no equilibrium index in the array.
//		
//				Input: arr[] = [-7, 1, 5, 2, -4, 3, 0]
//				Output: 3
		
		
		int[] arr = {1, 2, 0, 3};
		
		System.out.println("is "+findeql(arr));

		
	}
	
	public static int findeql(int[] arr) {
			int total = 0;
			
			for(int a: arr) total +=a;
			
			int leftSum = 0;
			
			for(int i = 0; i < arr.length; i++ ) {
				int rightSum = total - leftSum - arr[i];
				if(leftSum == rightSum) {
					return i;
				}
				leftSum += arr[i];
			}
			
			return -1;
	}
	

	
	
	

}
