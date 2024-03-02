/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.core.type.classreading;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.Nullable;

/**
 * Caching implementation of the {@link MetadataReaderFactory} interface,
 * caching a {@link MetadataReader} instance per Spring {@link Resource} handle
 * (i.e. per ".class" file).
 *
 *
 * CachingMetadataReaderFactory是一个用于缓存元数据读取器的工厂类。它是Spring Framework中的一个类，用于提高元数据读取的性能。
 *
 * 在Spring应用程序中，当需要读取类的元数据时，通常需要使用MetadataReaderFactory接口。而CachingMetadataReaderFactory是MetadataReaderFactory接口的一个实现类，它通过缓存已读取的元数据来减少重复的IO操作，从而提高性能。
 *
 * CachingMetadataReaderFactory内部使用了一个元数据读取器缓存，可以是ConcurrentMap类型或其他类型的缓存。当需要获取某个资源的元数据读取器时，首先会检查缓存中是否已经存在该资源的元数据读取器。如果存在，则直接从缓存中获取；如果不存在，则调用父类的getMetadataReader方法获取元数据读取器，并将其放入缓存中。
 *
 * 通过使用CachingMetadataReaderFactory，可以避免重复的IO操作，提高元数据读取的效率，特别是在需要频繁读取元数据的场景下，可以显著提升应用程序的性能。
 *
 * @author Juergen Hoeller
 * @author Costin Leau
 * @since 2.5
 */
public class CachingMetadataReaderFactory extends SimpleMetadataReaderFactory {

	/** Default maximum number of entries for a local MetadataReader cache: 256. */
	public static final int DEFAULT_CACHE_LIMIT = 256;

	/** MetadataReader cache: either local or shared at the ResourceLoader level. */
	@Nullable
	private Map<Resource, MetadataReader> metadataReaderCache;


	/**
	 * Create a new CachingMetadataReaderFactory for the default class loader,
	 * using a local resource cache.
	 */
	public CachingMetadataReaderFactory() {
		super();
		setCacheLimit(DEFAULT_CACHE_LIMIT);
	}

	/**
	 * Create a new CachingMetadataReaderFactory for the given {@link ClassLoader},
	 * using a local resource cache.
	 * @param classLoader the ClassLoader to use
	 */
	public CachingMetadataReaderFactory(@Nullable ClassLoader classLoader) {
		super(classLoader);
		setCacheLimit(DEFAULT_CACHE_LIMIT);
	}

	/**
	 * Create a new CachingMetadataReaderFactory for the given {@link ResourceLoader},
	 * using a shared resource cache if supported or a local resource cache otherwise.
	 * @param resourceLoader the Spring ResourceLoader to use
	 * (also determines the ClassLoader to use)
	 * @see DefaultResourceLoader#getResourceCache
	 */
	public CachingMetadataReaderFactory(@Nullable ResourceLoader resourceLoader) {
		super(resourceLoader);
		if (resourceLoader instanceof DefaultResourceLoader) {
			this.metadataReaderCache =
					((DefaultResourceLoader) resourceLoader).getResourceCache(MetadataReader.class);
		}
		else {
			setCacheLimit(DEFAULT_CACHE_LIMIT);
		}
	}


	/**
	 * Specify the maximum number of entries for the MetadataReader cache.
	 * <p>Default is 256 for a local cache, whereas a shared cache is
	 * typically unbounded. This method enforces a local resource cache,
	 * even if the {@link ResourceLoader} supports a shared resource cache.
	 */
	public void setCacheLimit(int cacheLimit) {
		if (cacheLimit <= 0) {
			this.metadataReaderCache = null;
		}
		else if (this.metadataReaderCache instanceof LocalResourceCache) {
			((LocalResourceCache) this.metadataReaderCache).setCacheLimit(cacheLimit);
		}
		else {
			this.metadataReaderCache = new LocalResourceCache(cacheLimit);
		}
	}

	/**
	 * Return the maximum number of entries for the MetadataReader cache.
	 */
	public int getCacheLimit() {
		if (this.metadataReaderCache instanceof LocalResourceCache) {
			return ((LocalResourceCache) this.metadataReaderCache).getCacheLimit();
		}
		else {
			return (this.metadataReaderCache != null ? Integer.MAX_VALUE : 0);
		}
	}


	/**
	 * 这段代码是一个方法，用于获取给定资源的元数据读取器。
	 * 它首先检查元数据读取器缓存是否是ConcurrentMap类型，如果是，则直接从缓存中获取元数据读取器。
	 * 如果缓存中没有找到元数据读取器，则调用父类的getMetadataReader方法获取元数据读取器，并将其放入缓存中。
	 * 如果元数据读取器缓存不是ConcurrentMap类型，则使用同步块来确保线程安全，然后执行相同的逻辑。
	 * 如果元数据读取器缓存为null，则直接调用父类的getMetadataReader方法获取元数据读取器。
	 * 最后，返回获取到的元数据读取器。
	 * @param resource the resource (pointing to a ".class" file)
	 * @return
	 * @throws IOException
	 */
	@Override
	public MetadataReader getMetadataReader(Resource resource) throws IOException {
		if (this.metadataReaderCache instanceof ConcurrentMap) {
			// No synchronization necessary...
			MetadataReader metadataReader = this.metadataReaderCache.get(resource);
			if (metadataReader == null) {
				metadataReader = super.getMetadataReader(resource);
				this.metadataReaderCache.put(resource, metadataReader);
			}
			return metadataReader;
		}
		else if (this.metadataReaderCache != null) {
			synchronized (this.metadataReaderCache) {
				MetadataReader metadataReader = this.metadataReaderCache.get(resource);
				if (metadataReader == null) {
					metadataReader = super.getMetadataReader(resource);
					this.metadataReaderCache.put(resource, metadataReader);
				}
				return metadataReader;
			}
		}
		else {
			return super.getMetadataReader(resource);
		}
	}

	/**
	 * Clear the local MetadataReader cache, if any, removing all cached class metadata.
	 */
	public void clearCache() {
		if (this.metadataReaderCache instanceof LocalResourceCache) {
			synchronized (this.metadataReaderCache) {
				this.metadataReaderCache.clear();
			}
		}
		else if (this.metadataReaderCache != null) {
			// Shared resource cache -> reset to local cache.
			setCacheLimit(DEFAULT_CACHE_LIMIT);
		}
	}


	@SuppressWarnings("serial")
	private static class LocalResourceCache extends LinkedHashMap<Resource, MetadataReader> {

		private volatile int cacheLimit;

		public LocalResourceCache(int cacheLimit) {
			super(cacheLimit, 0.75f, true);
			this.cacheLimit = cacheLimit;
		}

		public void setCacheLimit(int cacheLimit) {
			this.cacheLimit = cacheLimit;
		}

		public int getCacheLimit() {
			return this.cacheLimit;
		}

		@Override
		protected boolean removeEldestEntry(Map.Entry<Resource, MetadataReader> eldest) {
			return size() > this.cacheLimit;
		}
	}

}
