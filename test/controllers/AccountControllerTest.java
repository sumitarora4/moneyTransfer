package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.POST;
import static play.test.Helpers.PUT;
import static play.test.Helpers.route;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.node.ObjectNode;

import dao.H2DbConnection;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

public class AccountControllerTest extends WithApplication {

	@Override
	protected Application provideApplication() {
		return new GuiceApplicationBuilder().build();
	}

	@BeforeClass
	public static void setup() {

		H2DbConnection.populateTestData();
	}

	@Test
	public void testGetAccountById() {

		Http.RequestBuilder request = new Http.RequestBuilder().method(GET).uri("/accounts/102");

		Result result = route(app, request);
		assertEquals(OK, result.status());
	}

	@Test
	public void testDeposit() {

		final ObjectNode jsonNode = Json.newObject();
		jsonNode.put("accountId", 101);
		jsonNode.put("balance", 500.9);

		Http.RequestBuilder request = new Http.RequestBuilder().method(POST).bodyJson(jsonNode)
				.uri("/accounts/deposit");

		Result result = route(app, request);
		assertEquals(OK, result.status());
		assertTrue(result.contentType().isPresent());
		assertEquals("application/json", result.contentType().get());
	}

	@Test
	public void testWithdraw() {

		final ObjectNode jsonNode = Json.newObject();
		jsonNode.put("accountId", 102);
		jsonNode.put("balance", 50.67);

		Http.RequestBuilder request = new Http.RequestBuilder().method(POST).bodyJson(jsonNode)
				.uri("/accounts/withdraw");

		Result result = route(app, request);
		assertEquals(OK, result.status());
		assertTrue(result.contentType().isPresent());
		assertEquals("application/json", result.contentType().get());
	}

	@Test
	public void testTransferAmount() {

		final ObjectNode jsonNode = Json.newObject();
		jsonNode.put("fromAccountId", 101);
		jsonNode.put("toAccountId", 102);
		jsonNode.put("amount", 100.67);

		Http.RequestBuilder request = new Http.RequestBuilder().method(POST).bodyJson(jsonNode)
				.uri("/accounts/transferAmount");

		Result result = route(app, request);
		assertEquals(OK, result.status());
		assertTrue(result.contentType().isPresent());
		assertEquals("application/json", result.contentType().get());
	}
	
	
	@Test
    public void testGetAccountByWrongAccountId() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/accounts/100");

        Result result = route(app, request);
        assertEquals(NOT_FOUND, result.status());
        assertTrue(result.contentType().isPresent());
    }
	
	@Test
    public void testTransferAmountWithWrongRequestMethod() {
        final ObjectNode jsonNode = Json.newObject();
        jsonNode.put("fromAccountId", 101);
		jsonNode.put("toAccountId", 102);
		jsonNode.put("amount", 100.67);

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(PUT)
                .bodyJson(jsonNode)
                .uri("/accounts/transferAmount");

        Result result = route(app, request);
        assertEquals(NOT_FOUND, result.status());
        assertTrue(result.contentType().isPresent());
    }

}
