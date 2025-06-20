package com.sode.lsrevoker.service;

import com.sode.lsrevoker.client.FeignLink;
import com.sode.lsrevoker.entity.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevokerService {

	@Autowired
	private FeignLink linkResource;

	public void revokeAllLinks(List<Link> links) {

		for (Link link : links) {
			linkResource.revokeLink(link.getId());
		}

	}

	public List<Link> getAllLinks() {
		List<Link> links = linkResource.getAllLinks().getBody();
		return links;
	}

}
