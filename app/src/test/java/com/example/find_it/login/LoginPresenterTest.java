package com.example.find_it.login;

import com.example.find_it.login.model.ErrorResponse;
import com.example.find_it.login.model.LoginData;
import com.example.find_it.login.model.LoginResponse;
import com.example.find_it.service.FindItService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LoginPresenterTest {
    private final LoginView loginView = mock(LoginView.class);
    private MockWebServer mockWebServer;

    @Before
    public void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        FindItService.setBaseUrl(mockWebServer.url("/").toString());
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    private void setMockResponse(String mockResponse, int statusCode) {
        mockWebServer.enqueue(new MockResponse().setBody(mockResponse).setResponseCode(statusCode));
    }

    private void attemptUserLogin(LoginData loginData) throws InterruptedException {
        synchronized (loginView) {
            LoginPresenter loginPresenter = new LoginPresenter(loginView);
            loginPresenter.loginUser(loginData);
            loginView.wait();
        }
    }

    @Test
    public void givenCorrectLoginData_thenPresenterShouldCallOnLoginSuccessMethodInView() throws Exception {
        String mockResponseBody = "{\"access_token\":\"SOMELONGANDRANDOMLYGENERATEDSTRING\"," +
                "\"refresh_token\":\"SOMELONGANDRANDOMLYGENERATEDSTRING\"}";
        setMockResponse(mockResponseBody, 200);
        attemptUserLogin(new LoginData("username@gmail.com", "UserN4me"));

        verify(loginView).onLoginSuccess(ArgumentMatchers.eq(new LoginResponse(
                "SOMELONGANDRANDOMLYGENERATEDSTRING", "SOMELONGANDRANDOMLYGENERATEDSTRING"
        )));
        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("/login", request.getPath());
        String expectedRequest = "{\"email\":\"username@gmail.com\",\"password\":\"UserN4me\"}";
        assertEquals(expectedRequest, request.getBody().readUtf8());
    }

    @Test
    public void givenInCorrectLoginData_thenPresenterShouldCallOnLoginFailureMethodInView() throws Exception {
        String mockResponseBody = "{\"errors\":{\"email\":\"Incorrect email address!\"}}";
        setMockResponse(mockResponseBody, 400);
        attemptUserLogin(new LoginData("invalidEmail", ""));

        ErrorResponse expectedErrorResponse = new ErrorResponse();
        expectedErrorResponse.setErrors(new ErrorResponse.Errors(
                "Incorrect email address!", null
        ));
        verify(loginView).onLoginFailure(ArgumentMatchers.eq(expectedErrorResponse));

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("/login", request.getPath());
        String expectedRequest = "{\"email\":\"invalidEmail\",\"password\":\"\"}";
        assertEquals(expectedRequest, request.getBody().readUtf8());
    }

    @Test
    public void givenNetworkError_thenPresenterShouldCallOnNetworkErrorMethodInView() throws Exception {
        FindItService.setBaseUrl("http://unknownHost");

        synchronized (loginView) {
            LoginPresenter loginPresenter = new LoginPresenter(loginView);
            loginPresenter.loginUser(new LoginData("email", "password"));
            loginView.wait();

            verify(loginView).onNetworkError();
        }
    }
}
