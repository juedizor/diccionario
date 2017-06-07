package co.com.diccionario.utilidades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class CacheUtils {

	@Autowired
	private CacheManager cacheManager;

	public boolean clearCacheFromCacheName(final String cacheName) {
		final Cache cache = cacheManager.getCache(cacheName);
		if (cacheExists(cache)) {
			cache.clear();
			return true;
		}
		return false;
	}

	private Boolean cacheExists(final Cache cache) {
		return cache != null;
	}

}
