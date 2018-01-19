package com.zouhx.crawler.main;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) {
		new Thread(){
			@Override
		protected Object clone() throws CloneNotSupportedException {
			// TODO Auto-generated method stub
			return super.clone();
		}

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				System.out.println("1");
			}
			
		
		
		}.start();
	}
}
