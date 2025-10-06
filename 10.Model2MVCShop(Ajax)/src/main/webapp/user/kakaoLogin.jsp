<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.net.*,java.io.*,java.util.*,javax.net.ssl.HttpsURLConnection" %>
<%@ page import="com.google.gson.Gson" %> <%-- Gson 라이브러리 필요 --%>
<%@ page pageEncoding="UTF-8" %>

<%
    String clientId = "f38379dc4a1fd8db1c81e44d5bf62547";
    String redirectUri = "http://localhost:8080/user/kakaoLogin";
 
    String code = request.getParameter("code");
    String userId = null;

    if (code != null && !code.isEmpty()) {
        // 1. 액세스 토큰 요청 함수
        String getAccessToken(String codeParam) throws Exception {
            String tokenUrl = "https://kauth.kakao.com/oauth/token";
            URL url = new URL(tokenUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

            String postParams = "grant_type=authorization_code"
                    + "&client_id=" + clientId
                    + "&redirect_uri=" + redirectUri
                    + "&code=" + codeParam;

            try(OutputStream os = conn.getOutputStream()) {
                os.write(postParams.getBytes("UTF-8"));
                os.flush();
            }

            int responseCode = conn.getResponseCode();

            InputStream is = (responseCode == 200) ? conn.getInputStream() : conn.getErrorStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            br.close();

            return result.toString();
        }

        // 2. 사용자 정보 요청 함수
        String getUserInfo(String accessToken) throws Exception {
            String apiUrl = "https://kapi.kakao.com/v2/user/me";
            URL url = new URL(apiUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode();

            InputStream is = (responseCode == 200) ? conn.getInputStream() : conn.getErrorStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            br.close();

            return result.toString();
        }

        try {
            // 액세스 토큰 요청
            String tokenResponse = getAccessToken(code);

            // Gson으로 JSON 파싱
            Gson gson = new Gson();
            Map<String, Object> tokenMap = gson.fromJson(tokenResponse, Map.class);

            if(tokenMap.get("access_token") != null) {
                String accessToken = (String) tokenMap.get("access_token");

                // 사용자 정보 요청
                String userInfoResponse = getUserInfo(accessToken);
                Map<String, Object> userInfoMap = gson.fromJson(userInfoResponse, Map.class);

                // 예: user id 가져오기 (숫자형임)
                Double idDouble = (Double) userInfoMap.get("id");
                if(idDouble != null) {
                    userId = String.valueOf(idDouble.longValue());
                }

                // TODO: 실제 회원가입/로그인 처리 코드 위치
                // 예: DB에 userId 존재 확인 후 신규 회원 가입 or 로그인 처리
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>카카오 로그인 처리</title>
</head>
<body>
<script>
    <% if(userId != null) { %>
        if(window.opener){
            window.opener.postMessage({type:"KAKAO_LOGIN_SUCCESS", userId:"<%= userId %>"}, "*");
            window.close();
        } else {
            alert("부모창이 존재하지 않습니다.");
        }
    <% } else { %>
        alert("카카오 로그인에 실패했습니다.");
        if(window.opener) {
            window.close();
        }
    <% } %>
</script>
</body>
</html>
