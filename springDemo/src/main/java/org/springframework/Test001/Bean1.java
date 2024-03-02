package org.springframework.Test001;

import org.springframework.beans.factory.annotation.Autowired;

public  class Bean1 {

	@Autowired
	private Bean2 bean2;
	public Bean2 getBean2() {
		return bean2;
	}
}
