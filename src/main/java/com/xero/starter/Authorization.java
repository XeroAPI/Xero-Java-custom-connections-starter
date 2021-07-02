package main.java.com.xero.starter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.ClientCredentialsTokenRequest;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;

import com.xero.api.ApiClient;
import com.xero.api.client.IdentityApi;
import com.xero.models.identity.Connection;

@WebServlet("/Authorization")
public class Authorization extends HttpServlet {
    private static final long serialVersionUID = 1L;
    final String clientId = "---Enter your client id---";
    final String clientSecret = "---Enter your client secret---";
    final String TOKEN_SERVER_URL = "https://identity.xero.com/connect/token";
    final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    final JsonFactory JSON_FACTORY = new JacksonFactory();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Authorization() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<String> scopeList = new ArrayList<String>();
        scopeList.add("accounting.settings");
        scopeList.add("accounting.transactions");
        scopeList.add("accounting.contacts");
        scopeList.add("accounting.journals.read");
        scopeList.add("accounting.reports.read");
        scopeList.add("accounting.attachments");
        
        TokenStorage store = new TokenStorage();

        try {
                TokenResponse tokenResponse =
                new ClientCredentialsTokenRequest(HTTP_TRANSPORT, JSON_FACTORY,
                    new GenericUrl(TOKEN_SERVER_URL))
                    .setScopes(scopeList)
                    .setClientAuthentication(
                    new BasicAuthentication(clientId, clientSecret)).execute();
                System.out.println("Access token: " + tokenResponse.getAccessToken());
                ApiClient defaultIdentityClient = new ApiClient("https://api.xero.com", null, null, null, null);
                IdentityApi idApi = new IdentityApi(defaultIdentityClient);
                List<Connection> connection = idApi.getConnections(tokenResponse.getAccessToken(),null);
            
                store.saveItem(response, "jwt_token", tokenResponse.toPrettyString());
                store.saveItem(response, "access_token", tokenResponse.getAccessToken());
                store.saveItem(response, "refresh_token", tokenResponse.getRefreshToken());
                store.saveItem(response, "expires_in_seconds", tokenResponse.getExpiresInSeconds().toString());
                store.saveItem(response, "xero_tenant_id", connection.get(0).getTenantId().toString());

                response.sendRedirect("./AuthenticatedResource");
            } 
        catch (TokenResponseException e) {
                if (e.getDetails() != null) {
                    System.err.println("Error: " + e.getDetails().getError());
                    if (e.getDetails().getErrorDescription() != null) {
                        System.err.println(e.getDetails().getErrorDescription());
                    }
                    if (e.getDetails().getErrorUri() != null) {
                        System.err.println(e.getDetails().getErrorUri());
                    }
                } else {
                    System.err.println(e.getMessage());
                }
            }

    }
}
