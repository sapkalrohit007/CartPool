package com.cmpe275.sjsu.cartpool.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cmpe275.sjsu.cartpool.model.Pool;
import com.cmpe275.sjsu.cartpool.security.CurrentUser;
import com.cmpe275.sjsu.cartpool.security.UserPrincipal;
import com.cmpe275.sjsu.cartpool.service.PoolService;

@RestController
@RequestMapping("/pool")
public class PoolController {

	@Autowired
	private PoolService poolService;
	
	
	@PostMapping("/create")
	@PreAuthorize("hasRole('POOLER')")
	public Pool createPool(@CurrentUser UserPrincipal currentUser, @RequestBody Pool pool) {
		
		return poolService.createPool(currentUser, pool);
	}

	@DeleteMapping("/delete")
	@PreAuthorize("hasRole('POOLER')")
	public Pool deletePool(@CurrentUser UserPrincipal currentUser) {
		return poolService.deletePool(currentUser);
	}
}
