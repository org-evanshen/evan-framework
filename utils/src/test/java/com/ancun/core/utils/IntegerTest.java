package com.ancun.core.utils;

public class IntegerTest {


	public static void main(String[] args) {
		Integer i1 = 127; // 鑷姩瑁呯
		Integer i2 = 127;
		int j = 127;
		System.out.println(i1 == i2);
		System.out.println(i1 == j);

		Integer i3 = 128; // 鑷姩瑁呯
		Integer i4 = 128;
		int k = 128;
		System.out.println(i3 == i4);
		System.out.println(i4 == k);
		
//		褰撳皢涓€涓猧nt鍊艰祴鍊肩粰瀹冪殑涓€涓狪nteger鍖呰绫诲瀷鍙橀噺鏃讹紝Integer绫诲瀷璋冪敤浜唙alueOf鏂规硶锛?
//		Java浠ｇ爜 
//		   public static Integer valueOf(int i) {  
//		final int offset = 128;  
//		if (i >= -128 && i <= 127) { // must cache   
//		    return IntegerCache.cache[i + offset];  
//		}  
//		       return new Integer(i);  
		 
//		濡傛灉杩欎釜鍊煎湪-128鍜?27涔嬮棿锛屽垯灏嗚鍊艰繘琛屼簡缂撳瓨澶勭悊锛屽湪鍐呭瓨涓兘鏄寚鍚戠殑涓€涓寘瑁呯被鍨嬪璞?
//		 
//		涔熷氨鏄?
//		Integer i1=100;
//		Integer i2=100;
//		Integer i3=100;
//		Integer i4=100;
//		...
//		Integer i100=100;
//		 
//		杩欎簺鎵€鏈夌殑Integer瀵硅薄閮芥槸鎸囧悜鍚屼竴涓璞″湴鍧€绌洪棿鐨勩€?
//		 
//		浣嗘槸瓒呰繃浜嗚繖涓尯闂达紝鍒欏垎鍒垱寤虹殑鏄笉鍚岀殑Integer瀵硅薄.
//		鏌ョ湅IntegerCache涓紩鐢╟ache杩欎釜鍙橀噺鐨勯儴鍒嗭細
//		Java浠ｇ爜 
//		private IntegerCache(){}  
//		  
//		    static final Integer cache[] = new Integer[-(-128) + 127 + 1];  
//		  
//		    static {  
//		        for(int i = 0; i < cache.length; i++)  
//		        cache[i] = new Integer(i - 128);  
//		    }  
//		 
//		鐢变簬cache[]鍦↖ntegerCache绫讳腑鏄潤鎬佹暟缁勶紝涔熷氨鏄彧闇€瑕佸垵濮嬪寲涓€娆★紝鍗硈tatic锝?.....锝濋儴鍒嗭紝鎵€浠ワ紝濡傛灉Integer瀵硅薄鍒濆鍖栨椂鏄?128~127鐨勮寖鍥达紝灏变笉闇€瑕佸啀閲嶆柊瀹氫箟鐢宠绌洪棿锛岄兘鏄悓涓€涓璞?--鍦↖ntegerCache.cache涓紝杩欐牱鍙互鍦ㄤ竴瀹氱▼搴︿笂鎻愰珮鏁堢巼銆?

	}

}
