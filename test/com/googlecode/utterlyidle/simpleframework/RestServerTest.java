package com.googlecode.utterlyidle.simpleframework;

import com.googlecode.utterlyidle.HttpHeaders;
import com.googlecode.utterlyidle.HttpMessage;
import com.googlecode.utterlyidle.Request;
import com.googlecode.utterlyidle.Response;
import com.googlecode.utterlyidle.ServerContract;
import org.junit.Test;

import static com.googlecode.utterlyidle.Request.Builder.accept;
import static com.googlecode.utterlyidle.Parameters.Builder.add;
import static com.googlecode.utterlyidle.Request.get;
import static com.googlecode.utterlyidle.HttpMessage.Builder.header;
import static com.googlecode.utterlyidle.Status.NOT_ACCEPTABLE;
import static com.googlecode.utterlyidle.handlers.ClientHttpHandlerTest.handle;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

public class RestServerTest extends ServerContract<RestServer> {
    @Override
    protected Class<RestServer> server() throws Exception {
        return RestServer.class;
    }

    @Test
    public void willOnlyHandleSingleValueHeadersBecauseSimpleWebDoesntSupportIt() throws Exception {
        Response response = handle(Request.get("echoheaders", accept("*/*"), HttpMessage.Builder.header("someheader", "first value"), HttpMessage.Builder.header("someheader", "second value")), server);
        String result = response.entity().toString();

        assertThat(result, not(containsString("first value")));
        assertThat(result, containsString("second value"));
    }

    @Test
    public void willNotHandleMultipleAcceptHeaders() throws Exception {
        Response response = handle(Request.get("html", HttpMessage.Builder.header(add(HttpHeaders.ACCEPT, "text/plain"), add(HttpHeaders.ACCEPT, "text/html"), add(HttpHeaders.ACCEPT, "text/xml"))), server);
        assertThat(response.status(), is(NOT_ACCEPTABLE));
    }
}
