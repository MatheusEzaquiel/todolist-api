package br.com.mbe.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {
	
	//atribui as propriedades nulas para este obj
	public static void copyNonNulProperties(Object source, Object target) {
		BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
	}
	
	//Pega as propriedades nulas de um obj
	public static String[] getNullPropertyNames(Object source) {
		
		final BeanWrapper src = new BeanWrapperImpl(source);
		
		PropertyDescriptor[] pds = src.getPropertyDescriptors();
		
		Set<String> emptyNames = new HashSet<>();
		
		for(PropertyDescriptor pd: pds) {
			
			Object srcValue = src.getPropertyValue(pd.getName());
			
			if(srcValue == null) {
				emptyNames.add(pd.getName());
			}
				
		}

		String[] result = new String[emptyNames.size()];
		
		return emptyNames.toArray(result);
		
	}
}
