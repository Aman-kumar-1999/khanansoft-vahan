package com.example.demo;

import java.util.Arrays;
import java.util.List;

public class SingleTonDesingDemo {
	
	
	private static volatile SingleTonDesingDemo singleTonDesingDemo;
	
	
	private SingleTonDesingDemo(){
		
	}
	
	
	public static SingleTonDesingDemo getInscance() {
		
		
		
		if(singleTonDesingDemo == null) {
			
			synchronized (SingleTonDesingDemo.class) {
				
				if(singleTonDesingDemo == null) {
				
					singleTonDesingDemo = new SingleTonDesingDemo();
				
				}
			}
			
			
		}
		
		return singleTonDesingDemo;
	}
	
	
	
	
	List<Integer> num = Arrays.asList(1,3,5,6,7);
	
	int sum = num.stream().reduce(0, (a,b) -> a + b);
	
	
	

}
