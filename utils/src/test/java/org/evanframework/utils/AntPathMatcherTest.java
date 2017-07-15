package org.evanframework.utils;

import org.junit.Test;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class AntPathMatcherTest {

	
	
	@Test
	public void test() {
		PathMatcher p = new AntPathMatcher();
		
		System.out.println(p.match("/**/listb", "/listb")); 
	}

}
