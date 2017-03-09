package com.ancun.core.cache;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:test-bean.xml", "classpath*:cache-bean.xml" })
public class MemcacheTest {

	private static final Logger log = LoggerFactory.getLogger(MemcacheTest.class);

//	@Autowired
//	private MemCachedClient memCachedClient;
//
//	@Autowired
//	private MemcacheUtil memcacheUtil;
//
//	static {
//		//		String[] servers = { "127.0.0.1:11211" };
//		//
//		//		Integer[] weights = { 3 };
//		//
//		//		// 创建一个实例对象SockIOPool
//		//		SockIOPool pool = SockIOPool.getInstance("aa");
//		//
//		//		// set the servers and the weights
//		//		// 设置Memcached Server
//		//		pool.setServers(servers);
//		//		pool.setWeights(weights);
//		//
//		//		// set some basic pool settings
//		//		// 5 initial, 5 min, and 250 max conns
//		//		// and set the max idle time for a conn
//		//		// to 6 hours
//		//		pool.setInitConn(5);
//		//		pool.setMinConn(5);
//		//		pool.setMaxConn(250);
//		//		pool.setMaxIdle(1000 * 60 * 60 * 6);
//		//
//		//		// set the sleep for the maint thread
//		//		// it will wake up every x seconds and
//		//		// maintain the pool size
//		//		pool.setMaintSleep(30);
//		//
//		//		// Tcp的规则就是在发送一个包之前，本地机器会等待远程主机
//		//		// 对上一次发送的包的确认信息到来；这个方法就可以关闭套接字的缓存，
//		//		// 以至这个包准备好了就发；
//		//		pool.setNagle(false);
//		//		// 连接建立后对超时的控制
//		//		pool.setSocketTO(3000);
//		//		// 连接建立时对超时的控制
//		//		pool.setSocketConnectTO(0);
//		//
//		//		// initialize the connection pool
//		//		// 初始化一些值并与MemcachedServer段建立连接
//		//		pool.initialize();
//
//		//// lets set some compression on for the client
//		//// compress anything larger than 64k
//		// mcc.setCompressEnable(true);
//		// mcc.setCompressThreshold(64 * 1024);
//	}
//
//	@Test
//	public void test() throws IOException {
//		// MemcachedClient mc = new MemcachedClient(new
//		// InetSocketAddress("127.0.0.1", 11211));
//		// pool.initialize();
//		// MemCachedClient mcc = new MemCachedClient("aa");
//		// StringUtils.isEmpty("");
//
//		memCachedClient.set("a", 1);
//		memCachedClient.set("b", 2);
//		Object o = memCachedClient.get("a");
//		log.info("cache:" + o);
//	}
//
//	@Test
//	public void testMemcacheUtil() {
//		memcacheUtil.put("a", 1);
//		memcacheUtil.put("b", 2);
//		Object o = memCachedClient.get("a");
//		log.info("cache:" + o);
//	}
}
