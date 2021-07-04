package com.maybank.ims.utilities;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ItemAspect {
	@After(value="execution(* com.maybank.ims.service.ItemServiceImpl.*(..))")
	public void sayThanks() {
		System.out.println("### Thanks for using my method");
	}
}
