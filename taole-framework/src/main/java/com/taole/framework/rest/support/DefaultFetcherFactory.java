package com.taole.framework.rest.support;

import org.springframework.beans.factory.annotation.Autowired;

import com.taole.framework.protocol.ProtocolRegistry;
import com.taole.framework.rest.ResultSet.BeanRow;
import com.taole.framework.rest.ResultSet.Fetcher;
import com.taole.framework.rest.ResultSet.Row;


public class DefaultFetcherFactory {
	
	@Autowired
	private ProtocolRegistry protocolRegistry;

	public Fetcher<String> getUriFetcher() {
		return new Fetcher<String>() {
			public String fetch(Row row, Object propName) {
				if (row instanceof BeanRow) {
					Object delegate = ((BeanRow) row).getDelegate();
					return protocolRegistry.getURI(delegate);
				}
				return null;
			}
		};  
	}
	

}
