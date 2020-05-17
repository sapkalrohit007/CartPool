package com.cmpe275.sjsu.cartpool.controller;
import com.cmpe275.sjsu.cartpool.responsepojo.ErrorResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.cmpe275.sjsu.cartpool.model.Pool;
import com.cmpe275.sjsu.cartpool.responsepojo.CommonMessage;
import com.cmpe275.sjsu.cartpool.security.CurrentUser;
import com.cmpe275.sjsu.cartpool.security.UserPrincipal;
import com.cmpe275.sjsu.cartpool.service.PoolService;

import java.util.List;

@RestController
@RequestMapping("/pool")
@CrossOrigin(origins = "*")
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
	
	@PostMapping("/joinpool/{poolname}")
	@PreAuthorize("hasRole('POOLER')")
	public CommonMessage joinPool(@CurrentUser UserPrincipal currentUser,
								  @PathVariable(value = "poolname") String poolName,
								  @RequestParam(name = "refree",required=false) String refree
								){
		
		return poolService.joinPool(currentUser, poolName, refree);
		
	}
	
	@PostMapping("/leavepool")
	@PreAuthorize("hasRole('POOLER')")
	public CommonMessage leavePool(@CurrentUser UserPrincipal currentUser) {
		return poolService.leaveGroup(currentUser);
	}
	
	@RequestMapping(value="/confirm-request-owner", method= {RequestMethod.GET, RequestMethod.POST})
	public String confirmRequestAdmin(@RequestParam("token")String confirmationToken) {
		return poolService.confirmRequestAdmin(confirmationToken);
	}
	
	@RequestMapping(value="/confirm-request", method= {RequestMethod.GET, RequestMethod.POST})
	public String confirmRequestRefree(@RequestParam("token")String confirmationToken) {
		return poolService.confirmRequestRefree(confirmationToken);
	}
	
	@RequestMapping(value="/reject-request", method= {RequestMethod.GET, RequestMethod.POST})
	public String rejectRequestRefree(@RequestParam("token")String confirmationToken) {
		return poolService.rejectRequestRefree(confirmationToken);
	}

	@ApiOperation("API to search for pools. It accepts one of the 3 parameters - Name, Neighborhood and Zipcode")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Missing or Invalid parameters", response = ErrorResponse.class),
	})
	@GetMapping
	public List<Pool> getPoolByName(@RequestParam(name = "name",required=false) String poolName,
							  @RequestParam(name = "neighborhood",required=false) String neighborhoodName,
							  @RequestParam(name = "zipcode",required=false) String zipCode)
	{
		return poolService.getPool(poolName,neighborhoodName,zipCode);
	}
	
	@GetMapping("/allpools")
	public List<Pool> getPoolByName(){
		return poolService.getAllPool();
	}
	
	
}

