package ParserMethods;

import dto.HttpRequest;


public class ParserChecker {

    public void checker(HttpRequest httpRequest) {

        if (!httpRequest.valid) return;

        String method = httpRequest.method;
        String version = httpRequest.version;

        if (!("HTTP/1.1".equals(version) || "HTTP/1.0".equals(version))) {
            httpRequest.valid = false;
            httpRequest.status = 400;
            httpRequest.response = "Unsupported HTTP version";
            return;
        }

        if (!"GET".equals(method)) {
            httpRequest.valid = false;
            httpRequest.status = 405;
            httpRequest.response = "Method Not Supported Yet";
        }
    }
}
