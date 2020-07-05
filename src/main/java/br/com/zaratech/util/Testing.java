package br.com.zaratech.util;

import java.time.LocalDate;
import java.util.ArrayList;

public class Testing {

	public static void main(String[] args) {
		
		int parcelasInt = 6;
		ArrayList<Integer> parcelas = new ArrayList<Integer>();
		for(int i = 0; i < parcelasInt; i++) {
			parcelas.add(i);
			System.out.println(parcelas);
		}
		System.out.println(parcelas.size());
		
		LocalDate date = LocalDate.now();
		
		int quantidadeMeses = 3;
		int meses = 0;
		
		for (int i = 0; i < quantidadeMeses; i++) {
			
			meses++;
			
			date = LocalDate.now().plusMonths(meses);
		    System.out.println(date);
		}
	}
}
