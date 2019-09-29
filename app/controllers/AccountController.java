package controllers;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import model.Account;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import service.AccountService;
import utility.ResponseUtility;

public class AccountController extends Controller {
	
	private HttpExecutionContext ec;
	private AccountService accountService;
	
	@Inject
	public AccountController(HttpExecutionContext ec, AccountService accountService) {
		this.accountService = accountService;
		this.ec = ec;
		
	}
	
	public CompletionStage<Result> getAccount(long accountId){
		return supplyAsync(()  -> {
		final Optional<Account> accountData = accountService.getAccount(accountId);
		return accountData.map(row -> {
            JsonNode jsonObjects = Json.toJson(row);
            return ok(jsonObjects);
        }).orElse(notFound("Account with id:" + accountId + " not found"));
        }, ec.current());
				
				
	}
	
	/* public CompletionStage<Result> deposit(Http.Request request){
		JsonNode reqJson = request.body().asJson();
		
		return supplyAsync(()  -> {
			if (reqJson == null) {
				return badRequest(ResponseUtility.createResponse("Json is not correct",false));
			} 
		
		Optional<Account> accountData = accountService.deposit(Json.fromJson(reqJson, Account.class));
		
		return accountData.map(row -> {
//			if (row == null) {
//                return notFound(ResponseUtility.createResponse("Account Id not found",false));
//            }
			
            JsonNode jsonObjects = Json.toJson(row);
            return ok( ResponseUtility.createResponse(jsonObjects,true));
        }).orElse(internalServerError(ResponseUtility.createResponse("Could not deposit amount",false));
        }, ec.current());
				
				
	} */
	
	public CompletionStage<Result> deposit(Http.Request request) {
        JsonNode json = request.body().asJson();
        return supplyAsync(() -> {
            if (json == null) {
                return badRequest(ResponseUtility.createResponse("Expecting Json data", false));
            }
            Optional<Account> accountData = accountService.deposit(Json.fromJson(json, Account.class));
            return accountData.map(row -> {
                if (row == null) {
                    return notFound(ResponseUtility.createResponse("Account Id not found", false));
                }
                JsonNode jsonObject = Json.toJson(row);
                return ok(ResponseUtility.createResponse(jsonObject, true));
            }).orElse(internalServerError(ResponseUtility.createResponse("Could not deposit amount.", false)));
        }, ec.current());
    }

}
