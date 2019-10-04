package controllers;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;

import dao.AccountDaoImpl;
import model.Account;
import model.Transaction;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import service.AccountService;
import utility.ResponseUtility;

public class AccountController extends Controller {

	private static Logger log = Logger.getLogger(AccountController.class);
	
	/*
	 * Configure an HttpExecutionContext which will implement our
	 * actions using asynchronous, non-blocking code. This means that our action
	 * methods will return CompletionStage<Result> instead of just Result. This has
	 * the benefit of allowing us to write long-running tasks without blocking.
	 * 
	 * Note: There is just one caveat when dealing with asynchronous programming in a Play
	 * Framework controller: we have to provide an HttpExecutionContext. If we don’t
	 * supply the HTTP execution context, we'll get the infamous error “There is no
	 * HTTP Context available from here” when calling the action method.
	 */
	
	private HttpExecutionContext ec;
	private AccountService accountService;

	@Inject
	public AccountController(HttpExecutionContext ec, AccountService accountService) {
		this.accountService = accountService;
		this.ec = ec;

	}

	public CompletionStage<Result> getAccount(long accountId) {
		log.debug("inside controller getAccount");
		return supplyAsync(() -> {
			final Optional<Account> accountData = accountService.getAccount(accountId);
			return accountData.map(row -> {
				JsonNode jsonObjects = Json.toJson(row);
				return ok(jsonObjects);
			}).orElse(notFound("Account with id:" + accountId + " not found"));
		}, ec.current());

	}

	/*
	 * Injected Http.Request class to get the request body
	 * into Jackson's JsonNode class.
	 * 
	 * Returning a CompletionStage<Result>, which enables us to write
	 * non-blocking code using the CompletedFuture.supplyAsync method.
	 * 
	 * We can pass to it any String or a JsonNode, along with a boolean flag to
	 * indicate status.
	 * 
	 * Json.fromJson() to convert the incoming JSON object
	 * into a Account object and back to JSON for the response.
	 */
	
	public CompletionStage<Result> deposit(Http.Request request) {
		log.debug("inside controller deposit");
		JsonNode json = request.body().asJson();
		
		log.debug("RequestJson"+json);

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

	public CompletionStage<Result> withdraw(Http.Request request) {
		log.debug("inside controller withdraw");
		
		JsonNode json = request.body().asJson();
		log.debug("RequestJson"+json);

		return supplyAsync(() -> {
			if (json == null) {
				return badRequest(ResponseUtility.createResponse("Expecting Json data", false));
			}
			Optional<Account> accountData = accountService.withdraw(Json.fromJson(json, Account.class));
			return accountData.map(row -> {
				if (row == null) {
					return notFound(ResponseUtility.createResponse("Record not found", false));
				}
				JsonNode jsonObject = Json.toJson(row);
				return ok(ResponseUtility.createResponse(jsonObject, true));
			}).orElse(internalServerError(ResponseUtility.createResponse("Could not withdraw amount.", false)));
		}, ec.current());
	}

	
	/*
	 * API of transfering amount/balance from one account to another account
	 */	
	public CompletionStage<Result> transferAmount(Http.Request request) {
		
		log.debug("inside controller transferAmount");
		
		JsonNode json = request.body().asJson();
		log.debug("RequestJson"+json);

		return supplyAsync(() -> {
			if (json == null) {
				return badRequest(ResponseUtility.createResponse("Expecting Json data", false));
			}
			Optional<Account> accountData = accountService.transferAmount(Json.fromJson(json, Transaction.class));
			return accountData.map(row -> {
				if (row == null) {
					return notFound(ResponseUtility.createResponse("Record not found", false));
				}
				JsonNode jsonObject = Json.toJson(row);
				return ok(ResponseUtility.createResponse(jsonObject, true));
			}).orElse(internalServerError(ResponseUtility.createResponse("Could not transfer amount.", false)));
		}, ec.current());
	}

}
