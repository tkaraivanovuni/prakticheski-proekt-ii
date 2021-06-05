package pu.fmi.masters.openbanking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pu.fmi.masters.openbanking.service.OauthRequestService;

@RestController
public class RedirectController {
	
	@Autowired
	OauthRequestService oauthRequestService;
	
	@GetMapping(path = "/redirect")
	public String redirect(@RequestParam(value = "bank_id") int bankId) {
		return oauthRequestService.createOauthCodeRequest(bankId, "ais.sandbox", "dsksuccess");
	}

}
