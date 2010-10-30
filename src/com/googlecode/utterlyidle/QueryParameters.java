package com.googlecode.utterlyidle;

import com.googlecode.totallylazy.Pair;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.googlecode.totallylazy.Sequences.sequence;

public class QueryParameters extends Parameters {
    public static QueryParameters parse(String value){
         return (QueryParameters) sequence(UrlEncodedMessage.parse(value)).foldLeft(new QueryParameters(), pairIntoParameters());
    }

    public static QueryParameters queryParameters(Pair<String, String>... pairs) {
        return (QueryParameters) sequence(pairs).foldLeft(new QueryParameters(), pairIntoParameters());
    }

    @Override
    public String toString() {
        return "?" + UrlEncodedMessage.toString(this);
    }
}