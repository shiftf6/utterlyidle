package com.googlecode.utterlyidle;

import com.googlecode.totallylazy.Pair;
import com.googlecode.totallylazy.Strings;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.googlecode.totallylazy.Pair.pair;

public class UrlEncodedMessage {
    public static List<Pair<String, String>> parse(String value) {
        List<Pair<String, String>> result = new ArrayList<Pair<String, String>>();
        if (Strings.isEmpty(value)) {
            return result;
        }

        for (String pair : value.split("&")) {
            String[] nameValue = pair.split("=");
            if (nameValue.length == 1) {
                result.add(pair(decode(nameValue[0]), ""));
            }
            if (nameValue.length == 2) {
                result.add(pair(decode(nameValue[0]), decode(nameValue[1])));
            }
        }
        return result;
    }

    public static String toString(Iterable<? extends Pair<String, String>> pairs) {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (Pair<String, String> pair : pairs) {
            if (first) {
                first = false;
            } else {
                builder.append("&");
            }
            builder.append(encode(pair.first())).append("=").append(encode(pair.second()));
        }
        return builder.toString();
    }


    public static String decode(String value) {
        try {
            return URLDecoder.decode(value, Entity.DEFAULT_CHARACTER_SET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encode(String value) {
        if (value == null) return null;
        try {
            return URLEncoder.encode(value, Entity.DEFAULT_CHARACTER_SET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
