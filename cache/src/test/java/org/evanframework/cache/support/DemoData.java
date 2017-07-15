package org.evanframework.cache.support;

import java.math.BigDecimal;

public class DemoData {

	public static Demo randomDto() {
		Demo demo = new Demo();

		demo.setFieldText(randomTitle());
		demo.setFieldNumber(new BigDecimal(RandomData.randomInt(999999)));
		demo.setFieldRadio(EnumSex.MAN.getValue() + "");
		//demo.setFieldCheckboxArray(new EnumSex[] { EnumSex.WOMAN });
		demo.setFieldSelect(EnumSex.WOMAN.getValue() + "");
		demo.setFieldHtmleditorCut(RandomData.randomName("TestCut"));
		demo.setFieldHtmleditor(RandomData.randomName("TestContent"));
		demo.setFieldProvince("330000");
		demo.setFieldCity("330100");
		demo.setFieldRegion("330106");

		return demo;
	}

	//	public static DemoEntity randomEntity() {
	//		DemoEntity demo = new DemoEntity();
	//		demo.setFieldText(randomTitle());
	//		demo.setFieldNumber(new BigDecimal(RandomData.randomInt()));
	//		demo.setFieldRadio("2");
	//		demo.setFieldCheckbox("2");
	//		demo.setFieldSelect("2");
	//		demo.setFieldHtmleditorCut(RandomData.randomName("TestCut"));
	//		demo.setFieldHtmleditor(RandomData.randomName("TestContent"));
	//		demo.setFieldProvince("330000");
	//		demo.setFieldCity("330100");
	//		demo.setFieldRegion("330106");
	//		demo.setFieldDate(new Date());
	//		demo.setFieldDatetime(new Date());
	//
	//		return demo;
	//	}

	public static String randomTitle() {
		return RandomData.randomName("DEMO");
	}

}
