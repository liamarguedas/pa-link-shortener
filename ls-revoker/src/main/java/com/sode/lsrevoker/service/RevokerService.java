package com.sode.lsrevoker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sode.lsrevoker.client.FeignLink;
import com.sode.lsrevoker.entity.Link;

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
